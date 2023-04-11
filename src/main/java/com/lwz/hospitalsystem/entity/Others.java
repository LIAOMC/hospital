package com.lwz.hospitalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Others {
    private int id;         //编号
    private String name;    //姓名
    private String sex;     //性别
    private int age;        //年龄
    private String position;//岗位
    private String phone;   //电话号码
}
