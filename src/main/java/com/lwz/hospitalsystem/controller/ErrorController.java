package com.lwz.hospitalsystem.controller;


import com.lwz.hospitalsystem.common.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/401")
    public R<String> sendError(){
        return R.error("token过期！");
    }
}
