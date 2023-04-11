package com.lwz.hospitalsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient  {

    private int id;                     //编号
    private String name;                //病人姓名
    private String sex;                 //性别
    private int age;                    //年龄
    private String phone;               //电话
    private String idcard;            //身份证号码
    private LocalDateTime intime; //入院时间
    private LocalDateTime outtime;  //出院时间
    private String classic;             //科类
    private String reason;              //病因
    private int doctorid;               //主治医生ID
    private int houseid;                //病房ID
    private BigDecimal money;           //缴费金额
    private int bedid;                  //病床编号
}
