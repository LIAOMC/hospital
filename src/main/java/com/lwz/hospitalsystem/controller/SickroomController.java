package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.dto.PatientDto;
import com.lwz.hospitalsystem.dto.SickroomDto;
import com.lwz.hospitalsystem.entity.Office;
import com.lwz.hospitalsystem.entity.Sickbed;
import com.lwz.hospitalsystem.entity.Sickroom;
import com.lwz.hospitalsystem.service.OfficeService;
import com.lwz.hospitalsystem.service.SickbedService;
import com.lwz.hospitalsystem.service.SickroomService;
import com.lwz.hospitalsystem.utils.LoginToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sickroom")
public class SickroomController {
    @Autowired
    private SickroomService sickroomService;
    @Autowired
    private SickbedService sickbedService;
    @Autowired
    private OfficeService officeService;

    @GetMapping("/page")
    @LoginToken
    public R<Page> getAllSickroom(int page, int pageSize, String num){
        System.out.println("num的值为："+num);
        Page<Sickroom> pagInfo=new Page<>(page,pageSize);
        Page<SickroomDto> sickroomDtoPage = new Page<>();
        LambdaQueryWrapper<Sickroom> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(num!=null,Sickroom::getId,num);
        sickroomService.page(pagInfo,queryWrapper);
        BeanUtils.copyProperties(pagInfo,sickroomDtoPage,"records");
        List<Sickroom> records = pagInfo.getRecords();
        List<SickroomDto> sickroomDtos = records.stream().map((item) -> {
            SickroomDto sickroomDto = new SickroomDto();
            BeanUtils.copyProperties(item, sickroomDto);
            LambdaQueryWrapper<Sickbed> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Sickbed::getHousenumber, item.getId());
            int allcount = sickbedService.count(queryWrapper1);
            queryWrapper1.eq(Sickbed::getStatus, 0);
            int emptycount = sickbedService.count(queryWrapper1);
            sickroomDto.setAllSickbed(allcount);
            sickroomDto.setEmptySickbed(emptycount);
            return sickroomDto;
        }).collect(Collectors.toList());
        sickroomDtoPage.setRecords(sickroomDtos);
        return R.success(sickroomDtoPage);
    }

    @GetMapping("/searoom")
    public R<List> getByOfficeName(String name){
        LambdaQueryWrapper<Office> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Office::getName,name);
        Office office = officeService.getOne(queryWrapper);
        LambdaQueryWrapper<Sickroom> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.eq(Sickroom::getOfficeid,office.getId());
        List<Sickroom> list = sickroomService.list(queryWrapper1);
        List<Sickroom> sickrooms = list.stream().map((item) -> {
            LambdaQueryWrapper<Sickbed> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(Sickbed::getHousenumber, item.getId());
            queryWrapper2.eq(Sickbed::getStatus, 0);
            int count = sickbedService.count(queryWrapper2);
            if (count != 0) {
                return item;
            }
            return null;
        }).collect(Collectors.toList());
        return R.success(sickrooms);
    }

    @GetMapping("/seabed")
    public R<List> getBedByRoomId(int id){
        LambdaQueryWrapper<Sickbed> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Sickbed::getHousenumber,id);
        queryWrapper.eq(Sickbed::getStatus,0);
        List<Sickbed> list = sickbedService.list(queryWrapper);
        return R.success(list);
    }
}
