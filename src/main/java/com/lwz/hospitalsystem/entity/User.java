package com.lwz.hospitalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 3625894677207361354L;

    private int id;
    private String account;     //账号
    private String password;    //密码
    private String phone;       //电话号码
    private String idcard;     //身份证号码
    private int identity;   //0普通人，1管理员
    private String sex;         //性别
    private int age;            //年龄
    private LocalDateTime time; //注册时间
    private String name;        //管理员名字
}
