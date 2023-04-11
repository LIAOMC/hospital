package com.lwz.hospitalsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwz.hospitalsystem.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
