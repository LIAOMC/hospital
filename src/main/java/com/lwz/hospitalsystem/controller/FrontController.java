package com.lwz.hospitalsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lwz.hospitalsystem.common.R;
import com.lwz.hospitalsystem.entity.Patient;
import com.lwz.hospitalsystem.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/all")
public class FrontController {
    @Autowired
    private RegisterFormService registerFormService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private NurseService nurseService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/top")
    public R<Map> getAllInfo(){
        Map<String,Integer> map=new HashMap<>();
        map.put("挂号人数",registerFormService.count());
        map.put("医生人数",doctorService.count());
        map.put("护士人数",nurseService.count());
        map.put("手术人数",operationService.count());
        return R.success(map);
    }
    @GetMapping("circle")
    public R<Map> getInfo(){
        Map<String, Integer> map=new HashMap<>();
        LambdaQueryWrapper<Patient> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.isNull(Patient::getOuttime);
        map.put("住院人数",patientService.count(queryWrapper));
        queryWrapper.clear();
        queryWrapper.isNotNull(Patient::getOuttime);
        map.put("出院人数",patientService.count(queryWrapper));
        return R.success(map);
    }
}
