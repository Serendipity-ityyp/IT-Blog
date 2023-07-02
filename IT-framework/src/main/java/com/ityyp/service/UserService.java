package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-06-27 21:47:19
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
