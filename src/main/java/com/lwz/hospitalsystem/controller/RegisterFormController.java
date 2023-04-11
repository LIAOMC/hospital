package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.entity.RegisterForm;
import com.lwz.hospitalsystem.service.RegisterFormService;
import com.lwz.hospitalsystem.utils.LoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/register")
public class RegisterFormController {
    @Autowired
    private RegisterFormService registerFormService;

    @GetMapping("/page")
    @LoginToken
    public R<Page> getAllRegister(int page, int pageSize, String name){
        Page<RegisterForm> pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<RegisterForm> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(name!=null,RegisterForm::getName,name);
        registerFormService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PostMapping
    @LoginToken
    public R<String> saveRegister(@RequestBody RegisterForm registerForm){
        registerForm.setTime(LocalDateTime.now());
        registerFormService.save(registerForm);
        return R.success("添加成功！");
    }

    @GetMapping("/people")//挂号人数
    @LoginToken
    public R<Integer> getRegisterPeople(){
        LambdaQueryWrapper<RegisterForm> queryWrapper=new LambdaQueryWrapper<>();
        LocalDateTime time = LocalDateTime.now();
        String timeStr = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        queryWrapper.like(RegisterForm::getTime,timeStr);
        int count = registerFormService.count(queryWrapper);
        return R.success(count);
    }
}
