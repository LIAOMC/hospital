package com.lwz.hospitalsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.entity.User;
import com.lwz.hospitalsystem.service.TokenService;
import com.lwz.hospitalsystem.service.UserService;
import com.lwz.hospitalsystem.utils.LoginToken;
import com.lwz.hospitalsystem.utils.Md5Password;
import com.lwz.hospitalsystem.utils.SMSUtils;
import com.lwz.hospitalsystem.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public R<Object> login(@RequestBody Map map){
        String account = map.get("account").toString();
        String password=map.get("password").toString();
        JSONObject jsonObject=new JSONObject();
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        queryWrapper.eq(User::getPassword,password);
        User user = userService.getOne(queryWrapper);
        if(user==null){
            jsonObject.put("message","登录失败！");
            R r=new R();
            r.add("失败",jsonObject);
            return r.error("登录失败");
        }else {
            String token = tokenService.getToken(user);
            jsonObject.put("token", token);
            jsonObject.put("user", user);
            return R.success(jsonObject);
        }
    }

    @LoginToken
    @PostMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

    @GetMapping("/page")
    @LoginToken
    public R<Page> getAllUser(int page,int pageSize,String name){
        Page<User> pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(name!=null,User::getName,name);
        userService.page(pageInfo,queryWrapper);
        List<User> userList = pageInfo.getRecords();
        userList.forEach(s->s.setPassword(""));
        pageInfo.setRecords(userList);
        return R.success(pageInfo);
    }

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user) {
        //获取手机号
        String phone = user.getPhone();
        if (!StringUtils.isEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code：{}", code);
            //调用阿里云提供的短信API发送短信
            SMSUtils.sendMessage("瑞吉外卖", "\n" +
                    "SMS_271625052", phone, code);
            //需要将生成的验证码保存到Session
//            session.setAttribute(phone, code);
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.SECONDS);//设置验证码有效为5分钟
            return R.success("手机验证码发送成功！");
        }
        return R.error("手机验证码发送失败！");
    }

    @PutMapping
    @LoginToken
    public R<String> updatePassword(@RequestBody Map map){
        String phone=(String)map.get("phone");
        String oldPassword = (String) map.get("oldPassword");
        String newPassword = (String) map.get("newPassword");
        String code =(String) map.get("code");
        if(code!=redisTemplate.opsForValue().get(phone)){
            return R.error("验证码错误！");
        }
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        User user = userService.getOne(queryWrapper);
        queryWrapper.eq(User::getPhone,phone);
        if(Objects.equals(user.getPassword(), oldPassword)){
            user.setPassword(newPassword);
            userService.update(user,queryWrapper);
            return R.success("修改密码成功！");
        }else {
            return R.error("旧密码错误！");
        }
    }

    @DeleteMapping("/delete")
    @LoginToken
    public R<String> deleteUser(int id){
        userService.removeById(id);
        return R.success("删除成功！");
    }

    @PostMapping("/add")
    @LoginToken
    public R<String> addUser(@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,user.getName());
        User one = userService.getOne(queryWrapper);
        if(one!=null){
            return R.error("用户名已存在！");
        }
        user.setTime(LocalDateTime.now());
        user.setPassword(Md5Password.getMd5Password(user.getPassword()));
        userService.save(user);
        return R.success("添加用户成功！");
    }
}
