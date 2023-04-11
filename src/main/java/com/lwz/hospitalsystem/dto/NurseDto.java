package com.lwz.hospitalsystem.dto;

import com.lwz.hospitalsystem.entity.Nurse;
import lombok.Data;

@Data
public class NurseDto extends Nurse {
    private String office;
}
