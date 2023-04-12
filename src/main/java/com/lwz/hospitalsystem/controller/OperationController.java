package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.dto.OperationDto;
import com.lwz.hospitalsystem.entity.Operation;
import com.lwz.hospitalsystem.entity.OperationRoom;
import com.lwz.hospitalsystem.entity.Patient;
import com.lwz.hospitalsystem.service.OperationRoomService;
import com.lwz.hospitalsystem.service.OperationService;
import com.lwz.hospitalsystem.service.PatientService;
import com.lwz.hospitalsystem.utils.LoginToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    private OperationService operationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OperationRoomService operationRoomService;


    @GetMapping("/page")
    @LoginToken
    public R<Page> getOperation(int page,int pageSize,String name){
        System.out.println("name的值为："+name);
        Patient patient1=null;
        if(name!=null){
            LambdaQueryWrapper<Patient> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Patient::getName,name);
            patient1= patientService.getOne(queryWrapper);
            System.out.println("patient的值为："+patient1);
        }
        Page<Operation> pageInfo=new Page<>(page,pageSize);
        Page<OperationDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Operation> queryWrapper=new LambdaQueryWrapper<>();
        if(name!=null){
            queryWrapper.eq(Operation::getPatientid,patient1.getId());
        }
        operationService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Operation> records = pageInfo.getRecords();
        List<OperationDto> list = records.stream().map((item) -> {
            OperationDto operationDto = new OperationDto();
            BeanUtils.copyProperties(item, operationDto);
            Patient patient = patientService.getById(item.getPatientid());
            operationDto.setPatient(patient);
            return operationDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @PostMapping
    @LoginToken
    public R<String> addOperation(@RequestBody Operation operation){
        LambdaUpdateWrapper<OperationRoom> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(OperationRoom::getStatus,1);
        updateWrapper.eq(OperationRoom::getRoomid,operation.getOroomid());
        operationRoomService.update(updateWrapper);
        operation.setDatetime(LocalDateTime.now());
        operationService.save(operation);
        return R.success("添加手术信息成功！");
    }

    @PutMapping("/endoperation")
    public R<String> endOperation(@RequestBody Map map){
        Integer patientid = (Integer) map.get("patientid");
        Integer roomid = (Integer) map.get("roomid");
        UpdateWrapper<Operation> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("patientid",patientid);
        updateWrapper.set("outtime",LocalDateTime.now());
        operationService.update(null,updateWrapper);
        LambdaUpdateWrapper<OperationRoom> updateWrapper1=new LambdaUpdateWrapper<>();
        updateWrapper1.set(OperationRoom::getStatus,0);
        updateWrapper1.eq(OperationRoom::getRoomid,roomid);
        operationRoomService.update(null,updateWrapper1);
        return R.success("办理结束手术成功！");
    }

    @GetMapping("/people")
    @LoginToken
    public R<Integer> getOperation(){
        LambdaQueryWrapper<Operation> queryWrapper=new LambdaQueryWrapper<>();
        LocalDateTime time = LocalDateTime.now();
        String timeStr = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        queryWrapper.like(Operation::getDatetime,timeStr);
        int count = operationService.count(queryWrapper);
        System.out.println(count);
        return R.success(count);
    }

    @GetMapping("/list")
    @CrossOrigin
    public R<List> getAllOperation(){
        List<OperationRoom> list = operationRoomService.list();
        return R.success(list);
    }

}
