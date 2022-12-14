package com.example.users.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.users.model.TbUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {

}
