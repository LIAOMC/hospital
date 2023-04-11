package com.lwz.hospitalsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwz.hospitalsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
