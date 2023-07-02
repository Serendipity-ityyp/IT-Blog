package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ityyp.domain.pojo.LoginUser;
import com.ityyp.domain.pojo.User;
import com.ityyp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserName,username);
        User user = userMapper.selectOne(lqw);
        //判断是否查到用户 如果没有查到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //TODO 查询权限信息


        //返回用户
        return new LoginUser(user);
    }
}
