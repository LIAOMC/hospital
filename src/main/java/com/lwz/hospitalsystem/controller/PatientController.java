package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.dto.PatientDto;
import com.lwz.hospitalsystem.entity.Doctor;
import com.lwz.hospitalsystem.entity.Patient;
import com.lwz.hospitalsystem.entity.Sickroom;
import com.lwz.hospitalsystem.service.DoctorService;
import com.lwz.hospitalsystem.service.PatientService;
import com.lwz.hospitalsystem.service.SickroomService;
import com.lwz.hospitalsystem.utils.LoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private SickroomService sickroomService;


    @GetMapping("/page/{num}")
    @LoginToken
    public R<Page> getPatient(int page,int pageSize,@PathVariable int num,String name){
        Page<Patient> pageInfo=new Page<>(page,pageSize);
        Page<PatientDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Patient> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.isNull(num==1,Patient::getOuttime);
        queryWrapper.isNotNull(num==0,Patient::getOuttime);
        if(name!=null){
            queryWrapper.eq(Patient::getName,name);
        }
        patientService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Patient> records = pageInfo.getRecords();
        List<PatientDto> list=records.stream().map((item)->{
            PatientDto patientDto=new PatientDto();
            BeanUtils.copyProperties(item,patientDto);
            System.out.println("号为："+item.getDoctorid()+""+item.getHouseid());
            Doctor doctor = doctorService.getById(item.getDoctorid());
            Sickroom sickroom = sickroomService.getById(item.getHouseid());
            if(doctor!=null){
                patientDto.setRoomclassic(sickroom.getRoomclassic());
                patientDto.setSickroomAddress(sickroom.getAddress());
                patientDto.setDoctorName(doctor.getName());
            }
            return patientDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @PostMapping
    @LoginToken
    public R<String> svePatient(@RequestBody PatientDto patientDto){
        System.out.println(patientDto);
        patientService.savePatient(patientDto);
        return R.success("添加成功！");
    }

    @PutMapping
    @LoginToken
    public R<String> updatePatient(@RequestBody PatientDto patientDto){
        patientService.updatePatient(patientDto);
        return R.success("修改成功！");
    }

    @PostMapping("/{id}")
    @LoginToken
    public R<String> outPatient(@PathVariable int id){
        LambdaUpdateWrapper<Patient> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(Patient::getOuttime, LocalDateTime.now());
        updateWrapper.eq(Patient::getId,id);
        patientService.update(null,updateWrapper);
        return R.success("办理出院成功!");
    }

    @GetMapping("/people")//住院人数
    @LoginToken
    public R<Integer> getInPeople(){
        LambdaQueryWrapper<Patient> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.isNull(Patient::getOuttime);
        int count = patientService.count(queryWrapper);
        return R.success(count);
    }

    @GetMapping("/outpeople")//住院人数
    @LoginToken
    public R<Integer> getOutPeople(){
        LambdaQueryWrapper<Patient> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(Patient::getOuttime);
        int count = patientService.count(queryWrapper);
        return R.success(count);
    }


}
