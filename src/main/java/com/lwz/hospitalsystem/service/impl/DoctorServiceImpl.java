package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.dto.DoctorDto;
import com.lwz.hospitalsystem.entity.Doctor;
import com.lwz.hospitalsystem.entity.Office;
import com.lwz.hospitalsystem.mapper.DoctorMapper;
import com.lwz.hospitalsystem.service.DoctorService;
import com.lwz.hospitalsystem.service.OfficeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
    @Autowired
    private OfficeService officeService;

    @Override
    public void updateDoctor(DoctorDto doctorDto) {
        LambdaQueryWrapper<Office> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Office::getName,doctorDto.getOffice());
        Office office = officeService.getOne(queryWrapper);
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorDto, doctor);
        doctor.setOfficeid(office.getId());
        LambdaQueryWrapper<Doctor> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Doctor::getId, doctorDto.getId());
        this.update(doctor, queryWrapper1);
    }
}
