package com.lwz.hospitalsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwz.hospitalsystem.entity.Doctor;
import com.lwz.hospitalsystem.entity.Nurse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NurseMapper extends BaseMapper<Nurse> {
}
