package com.ityyp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.domain.pojo.User;
import com.ityyp.service.UserService;
import com.ityyp.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-06-27 21:47:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




