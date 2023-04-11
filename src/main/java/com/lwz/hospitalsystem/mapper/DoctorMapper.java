package com.lwz.hospitalsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwz.hospitalsystem.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
}
