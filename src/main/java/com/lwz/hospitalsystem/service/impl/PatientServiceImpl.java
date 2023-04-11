package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.dto.PatientDto;
import com.lwz.hospitalsystem.entity.Doctor;
import com.lwz.hospitalsystem.entity.Patient;
import com.lwz.hospitalsystem.entity.Sickbed;
import com.lwz.hospitalsystem.entity.Sickroom;
import com.lwz.hospitalsystem.mapper.PatientMapper;
import com.lwz.hospitalsystem.service.DoctorService;
import com.lwz.hospitalsystem.service.PatientService;
import com.lwz.hospitalsystem.service.SickbedService;
import com.lwz.hospitalsystem.service.SickroomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private SickbedService sickbedService;
    @Autowired
    private SickroomService sickroomService;
    @Override
    public void savePatient(PatientDto patientDto) {
        Patient patient=new Patient();
        BeanUtils.copyProperties(patientDto,patient);
        patient.setIntime(LocalDateTime.now());
        this.save(patient);
        UpdateWrapper<Sickbed> queryWrapper=new UpdateWrapper<>();
        queryWrapper.set("status","1");
        queryWrapper.eq("id",patientDto.getBedid());
        sickbedService.update(null,queryWrapper);
    }

    @Override
    public void updatePatient(PatientDto patientDto) {
        Patient patient=new Patient();
        BeanUtils.copyProperties(patientDto,patient);
        LambdaUpdateWrapper<Patient> queryWrapper1=new LambdaUpdateWrapper<>();
        queryWrapper1.eq(Patient::getId,patientDto.getId());
        this.update(patient,queryWrapper1);
        LambdaUpdateWrapper<Sickroom> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Sickroom::getId,patientDto.getHouseid());
        updateWrapper.set(Sickroom::getRoomclassic,patientDto.getRoomclassic());
        sickroomService.update();
    }
}
