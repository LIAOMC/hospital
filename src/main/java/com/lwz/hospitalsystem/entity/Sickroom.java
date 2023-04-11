package com.lwz.hospitalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sickroom {
    private int id; //病房号
    private String address;     //病房地址
    private int officeid;       //科室ID
    private int roomclassic;           //病房类型0普1重

}
