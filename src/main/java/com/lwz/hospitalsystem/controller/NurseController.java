package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.dto.NurseDto;
import com.lwz.hospitalsystem.entity.Nurse;
import com.lwz.hospitalsystem.entity.Office;
import com.lwz.hospitalsystem.service.NurseService;
import com.lwz.hospitalsystem.service.OfficeService;
import com.lwz.hospitalsystem.utils.LoginToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nurse")
public class NurseController {
    @Autowired
    private NurseService nurseService;
    @Autowired
    private OfficeService officeService;

    @GetMapping("/page")
    @LoginToken
    public R<Page> getAllNurse(int page, int pageSize, String name){
        Page<Nurse> pageInfo =new Page<>(page,pageSize);
        Page<NurseDto> pageInfo2=new Page<>();
        LambdaQueryWrapper<Nurse> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Nurse::getName,name);
        nurseService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,pageInfo2,"records");
        List<Nurse> nurseList = pageInfo.getRecords();
        List<NurseDto> list = nurseList.stream().map((item) -> {
            NurseDto nurseDto = new NurseDto();
            BeanUtils.copyProperties(item, nurseDto);
            Office office = officeService.getById(item.getOfficeid());
            nurseDto.setOffice(office.getName());
            return nurseDto;
        }).collect(Collectors.toList());
        pageInfo2.setRecords(list);
        return R.success(pageInfo2);
    }

    @PutMapping
    @LoginToken
    public R<String> updateNurse(@RequestBody NurseDto nurseDto){
        nurseService.updateNurse(nurseDto);
        return R.success("修改成功！");
    }

    @DeleteMapping
    @LoginToken
    public R<String> deleteNurse(int nurseId){
        nurseService.removeById(nurseId);
        return R.success("删除成功！");
    }

    @PostMapping
    @LoginToken
    public R<String> addDoctor(@RequestBody NurseDto nurseDto) {
        LambdaQueryWrapper<Office> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Office::getName,nurseDto.getOffice());
        Office office = officeService.getOne(queryWrapper);
        Nurse nurse=new Nurse();
        BeanUtils.copyProperties(nurseDto,nurse);
        nurse.setOfficeid(office.getId());
        nurseService.save(nurse);
        return R.success("添加护士成功！");
    }

    @GetMapping("/people")
    @LoginToken
    public R<Integer> getAllNurse(){
        int count = nurseService.count();
        return R.success(count);
    }
}
