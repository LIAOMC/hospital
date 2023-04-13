package com.lwz.hospitalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nurse {
    @TableId(type = IdType.AUTO)
    private int id;         //编号
    private String name;    //护士姓名
    private String sex;     //性别
    private String job;     //职位
    private int   age;      //年龄
    private String phone;   //电话号码
    private String img;     //照片
    private int officeid;   //科室ID
}
