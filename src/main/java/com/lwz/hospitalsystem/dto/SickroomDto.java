package com.lwz.hospitalsystem.dto;

import com.lwz.hospitalsystem.entity.Sickroom;
import lombok.Data;

@Data
public class SickroomDto extends Sickroom {
    private int allSickbed;     //病床总数量
    private int  emptySickbed;  //空闲的病床数量
}
