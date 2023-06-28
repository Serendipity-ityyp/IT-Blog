package com.ityyp.mapper;

import com.ityyp.domain.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-06-27 21:47:19
* @Entity com.ityyp.domain.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




