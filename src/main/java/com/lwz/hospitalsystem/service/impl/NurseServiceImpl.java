package com.lwz.hospitalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwz.hospitalsystem.dto.NurseDto;
import com.lwz.hospitalsystem.entity.Doctor;
import com.lwz.hospitalsystem.entity.Nurse;
import com.lwz.hospitalsystem.entity.Office;
import com.lwz.hospitalsystem.mapper.NurseMapper;
import com.lwz.hospitalsystem.service.NurseService;
import com.lwz.hospitalsystem.service.OfficeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl extends ServiceImpl<NurseMapper, Nurse> implements NurseService {
    @Autowired
    private OfficeService officeService;

    @Override
    public void updateNurse(NurseDto nurseDto) {
        Office office = officeService.getById(nurseDto.getOfficeid());
        Nurse nurse = new Nurse();
        BeanUtils.copyProperties(nurseDto, nurse);
        nurse.setOfficeid(office.getId());
        LambdaQueryWrapper<Nurse> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Nurse::getId, nurseDto.getId());
        this.update(nurse, queryWrapper1);
    }
}
