package com.ityyp.controller;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.LoginUser;
import com.ityyp.domain.pojo.Menu;
import com.ityyp.domain.pojo.User;
import com.ityyp.domain.vo.AdminUserInfoVo;
import com.ityyp.domain.vo.RoutersVo;
import com.ityyp.domain.vo.UserInfoVo;
import com.ityyp.enums.AppHttpCodeEnum;
import com.ityyp.exception.SystemException;
import com.ityyp.service.LoginService;
import com.ityyp.service.MenuService;
import com.ityyp.service.RoleService;
import com.ityyp.utils.BeanCopyUtils;
import com.ityyp.utils.SecurityUtils;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> rolesKeyLists = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
//        List<String> rolesKeyLists = null;
        //封装数据返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, rolesKeyLists, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }


}
