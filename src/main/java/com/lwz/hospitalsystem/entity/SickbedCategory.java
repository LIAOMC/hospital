package com.lwz.hospitalsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SickbedCategory {
    private int id;             //编号 0普1重
    private BigDecimal price;   //病床价格
}
