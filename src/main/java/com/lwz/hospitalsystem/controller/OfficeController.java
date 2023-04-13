package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.entity.Office;
import com.lwz.hospitalsystem.service.OfficeService;
import com.lwz.hospitalsystem.utils.LoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/office")
public class OfficeController {
    @Autowired
    private OfficeService officeService;


    @GetMapping("/page")
    @LoginToken
    public R<Page> getOffices(int page,int pageSize,String name){
        log.info("页数为："+page+",每页个数为："+pageSize+",科室名为："+name);
        Page pageInfo =new Page(page,pageSize);
        LambdaQueryWrapper<Office> queryWrapper=new LambdaQueryWrapper<>();
        if(name!=null){
            queryWrapper.like(Office::getName,name);
        }
        officeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @GetMapping("/list")
    public R<List> getOffice(int officekind){
        LambdaQueryWrapper<Office> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Office::getOfficekind,officekind);
        List<Office> list = officeService.list(queryWrapper);
        return R.success(list);
    }


}
