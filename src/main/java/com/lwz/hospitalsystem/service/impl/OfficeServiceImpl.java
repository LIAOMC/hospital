package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.entity.Office;
import com.lwz.hospitalsystem.mapper.OfficeMapper;
import com.lwz.hospitalsystem.service.OfficeService;
import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements OfficeService {

}
