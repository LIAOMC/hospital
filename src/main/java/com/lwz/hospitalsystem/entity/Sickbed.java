package com.lwz.hospitalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sickbed {
    private int id;     //编号
    private int status; //病床状态,0无人、1有人
    private int housenumber;    //所在的病房号
    private int catrgoryid;     //病床分类
}
