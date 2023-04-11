package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.entity.OperationRoom;
import com.lwz.hospitalsystem.mapper.OperationRoomMapper;
import com.lwz.hospitalsystem.service.OperationRoomService;
import org.springframework.stereotype.Service;

@Service
public class OperationRoomServiceImpl extends ServiceImpl<OperationRoomMapper, OperationRoom> implements OperationRoomService {
}
