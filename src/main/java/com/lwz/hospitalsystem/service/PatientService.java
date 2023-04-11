package com.lwz.hospitalsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwz.hospitalsystem.dto.PatientDto;
import com.lwz.hospitalsystem.entity.Patient;

public interface PatientService extends IService<Patient> {
    public void savePatient(PatientDto patientDto);
    public void updatePatient(PatientDto patientDto);
}
