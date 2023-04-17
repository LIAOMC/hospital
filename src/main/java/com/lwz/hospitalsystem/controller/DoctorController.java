package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.dto.DoctorDto;
import com.lwz.hospitalsystem.entity.Doctor;
import com.lwz.hospitalsystem.entity.Office;
import com.lwz.hospitalsystem.service.DoctorService;
import com.lwz.hospitalsystem.service.OfficeService;
import com.lwz.hospitalsystem.utils.LoginToken;
import com.lwz.hospitalsystem.utils.PassToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private OfficeService officeService;

    @GetMapping("/page")
    @LoginToken
    public R<Page> getAllDoctor(int page,int pageSize,String name){
        Page<Doctor> pageInfo =new Page<>(page,pageSize);
        Page<DoctorDto> pageInfo2=new Page<>();
        LambdaQueryWrapper<Doctor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Doctor::getName,name);
        doctorService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,pageInfo2,"records");
        List<Doctor> doctorList = pageInfo.getRecords();
        List<DoctorDto> list = doctorList.stream().map((item) -> {
            DoctorDto doctorDto = new DoctorDto();
            BeanUtils.copyProperties(item, doctorDto);
            Office office = officeService.getById(item.getOfficeid());
            doctorDto.setOffice(office.getName());
            return doctorDto;
        }).collect(Collectors.toList());
        pageInfo2.setRecords(list);
        return R.success(pageInfo2);
    }

    @PutMapping
    @LoginToken
    public R<String> updateDoctor(@RequestBody DoctorDto doctorDto){
        doctorService.updateDoctor(doctorDto);
        return R.success("修改成功！");
    }

    @DeleteMapping
    @LoginToken
    public R<String> deleteDoctor(int doctorId){
        doctorService.removeById(doctorId);
        return R.success("删除成功！");
    }

    @PostMapping
    @LoginToken
    public R<String> addDoctor(@RequestBody DoctorDto doctorDto) {
        LambdaQueryWrapper<Office> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Office::getName,doctorDto.getOffice());
        Office office = officeService.getOne(queryWrapper);
        Doctor doctor=new Doctor();
        BeanUtils.copyProperties(doctorDto,doctor);
        doctor.setOfficeid(office.getId());
        doctorService.save(doctor);
        return R.success("添加医生成功！");
    }

    @GetMapping("/people")
    @LoginToken
    public R<Integer> getAllDoctor(){
        int count = doctorService.count();
        return R.success(count);
    }


    @GetMapping("/jobtitle")//住院人数
    @LoginToken
    public R<Map> getOutPeople(){
        int count;
        Map<String,Integer> map=new HashMap<>();
        LambdaQueryWrapper<Doctor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Doctor::getJobtitle,"教授");
        count = doctorService.count(queryWrapper);
        map.put("教授",count);
        queryWrapper.clear();
        queryWrapper.eq(Doctor::getJobtitle,"医师");
        count = doctorService.count(queryWrapper);
        map.put("医师",count);
        queryWrapper.clear();
        queryWrapper.eq(Doctor::getJobtitle,"主任");
        count = doctorService.count(queryWrapper);
        map.put("主任",count);
        return R.success(map);
    }

    @GetMapping("/info")
    @PassToken
    public R<Page> doctorInfo(int page){
        Page<Doctor> pageInfo=new Page<>(page,8);
        doctorService.page(pageInfo);
        return R.success(pageInfo);
    }
}
