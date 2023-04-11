package com.lwz.hospitalsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("registerform")
public class RegisterForm {
    private BigInteger formid;                  //挂号单单号
    private String name;                 //名字
    private String sex;                  //性别
    private int age;                     //年龄
    private String phone;                //电话
    private String idcard;               //身份证号码
    private String way;                  //挂号方式
    private String officename;                //科室ID
    private LocalDateTime time;          //挂号时间
}
