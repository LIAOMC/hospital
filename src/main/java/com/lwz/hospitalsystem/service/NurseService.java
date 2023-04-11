package com.lwz.hospitalsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwz.hospitalsystem.dto.DoctorDto;
import com.lwz.hospitalsystem.dto.NurseDto;
import com.lwz.hospitalsystem.entity.Doctor;
import com.lwz.hospitalsystem.entity.Nurse;

public interface NurseService extends IService<Nurse> {
    public void updateNurse(NurseDto nurseDto);
}
