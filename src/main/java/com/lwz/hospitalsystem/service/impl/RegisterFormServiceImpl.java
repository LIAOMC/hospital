package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.entity.RegisterForm;
import com.lwz.hospitalsystem.mapper.RegisterFormMapper;
import com.lwz.hospitalsystem.service.RegisterFormService;
import org.springframework.stereotype.Service;

@Service
public class RegisterFormServiceImpl extends ServiceImpl<RegisterFormMapper, RegisterForm> implements RegisterFormService {
}
