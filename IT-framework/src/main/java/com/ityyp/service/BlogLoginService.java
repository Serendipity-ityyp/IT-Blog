package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
