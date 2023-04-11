package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.entity.Sickbed;
import com.lwz.hospitalsystem.mapper.SickbedMapper;
import com.lwz.hospitalsystem.service.SickbedService;
import org.springframework.stereotype.Service;

@Service
public class SickbedServiceImpl extends ServiceImpl<SickbedMapper, Sickbed> implements SickbedService {
}
