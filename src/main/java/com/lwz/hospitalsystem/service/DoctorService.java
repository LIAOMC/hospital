package com.lwz.hospitalsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwz.hospitalsystem.dto.DoctorDto;
import com.lwz.hospitalsystem.entity.Doctor;

public interface DoctorService extends IService<Doctor> {
    public void updateDoctor(DoctorDto doctorDto);
}
