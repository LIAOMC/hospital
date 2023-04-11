package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.entity.Operation;
import com.lwz.hospitalsystem.mapper.OperationMapper;
import com.lwz.hospitalsystem.service.OperationService;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {
}
