package com.ityyp.mapper;

import com.ityyp.domain.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2023-07-18 19:56:49
* @Entity com.ityyp.domain.pojo.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




