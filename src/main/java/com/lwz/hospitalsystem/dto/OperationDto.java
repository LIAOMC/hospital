package com.lwz.hospitalsystem.dto;

import com.lwz.hospitalsystem.entity.Operation;
import com.lwz.hospitalsystem.entity.Patient;
import lombok.Data;

@Data
public class OperationDto extends Operation {
    private Patient patient;
}
