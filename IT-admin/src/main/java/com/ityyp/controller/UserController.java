package com.ityyp.controller;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.User;
import com.ityyp.service.UserService;
import com.ityyp.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult listAllUser(Integer pageNum, Integer pageSize, User user){
        return userService.listAllUser(pageNum,pageSize,user);
    }

    @PostMapping()
    public ResponseResult addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteUserById(@PathVariable List<Long> ids){
        return userService.deleteUserById(ids);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserInfoById(@PathVariable Long id){
        return userService.getUserInfoById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
