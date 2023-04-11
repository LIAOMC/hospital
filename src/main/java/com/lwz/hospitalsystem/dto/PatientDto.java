package com.lwz.hospitalsystem.dto;

import com.lwz.hospitalsystem.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto extends Patient {
    private String DoctorName;
    private String sickroomAddress;
    private int roomclassic;
}
