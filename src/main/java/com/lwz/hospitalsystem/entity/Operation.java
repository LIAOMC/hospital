package com.lwz.hospitalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @TableId(type = IdType.AUTO)
    private int id;                     //编号
    private LocalDateTime datetime;     //手术时间
    private BigDecimal price;           //价格
    private int patientid;              //病人ID
    private int oroomid;                //手术室编号
    private String doctor;              //主治医生
}
