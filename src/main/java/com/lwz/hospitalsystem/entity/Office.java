package com.lwz.hospitalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Office {
    private int id;         //科室
    private String name;    //科室名
    private String address; //地址
    private String phone;   //电话号码
    private String officedoctor;    //科室主任
    private int sickroomnum;        //科室病房数量
    private String officeinfo;      //科室信息

}
