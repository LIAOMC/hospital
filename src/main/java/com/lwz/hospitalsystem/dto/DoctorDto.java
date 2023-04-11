package com.lwz.hospitalsystem.dto;

import com.lwz.hospitalsystem.entity.Doctor;
import lombok.Data;

@Data
public class DoctorDto extends Doctor {
    private String office;
}
