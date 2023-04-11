package com.lwz.hospitalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private int id;         //编号
    private String name;    //医生名字
    private String sex;     //性别
    private int age;        //年龄
    private String jobtitle;//职称
    private String phone;   //电话
    private String img;     //头像
    private int officeid;   //科室ID
    private String info;

}
