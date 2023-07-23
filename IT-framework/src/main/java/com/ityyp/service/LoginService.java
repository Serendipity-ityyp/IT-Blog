package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}