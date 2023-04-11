package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.entity.Sickroom;
import com.lwz.hospitalsystem.mapper.SickroomMapper;
import com.lwz.hospitalsystem.service.SickroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class SickroomServiceImpl extends ServiceImpl<SickroomMapper, Sickroom> implements SickroomService {
}
