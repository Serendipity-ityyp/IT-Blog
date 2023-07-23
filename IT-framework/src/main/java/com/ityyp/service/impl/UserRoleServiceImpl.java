package com.ityyp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.domain.pojo.UserRole;
import com.ityyp.service.UserRoleService;
import com.ityyp.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-07-18 19:56:49
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




