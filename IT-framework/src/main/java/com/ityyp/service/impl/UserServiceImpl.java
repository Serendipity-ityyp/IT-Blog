package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.constants.SystemConstants;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Role;
import com.ityyp.domain.pojo.User;
import com.ityyp.domain.pojo.UserRole;
import com.ityyp.domain.vo.PageVo;
import com.ityyp.domain.vo.UserInfoVo;
import com.ityyp.domain.vo.UserVo;
import com.ityyp.enums.AppHttpCodeEnum;
import com.ityyp.exception.SystemException;
import com.ityyp.service.RoleService;
import com.ityyp.service.UserRoleService;
import com.ityyp.service.UserService;
import com.ityyp.mapper.UserMapper;
import com.ityyp.utils.BeanCopyUtils;
import com.ityyp.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2023-06-27 21:47:19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //邮箱
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllUser(Integer pageNum, Integer pageSize, User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(user.getUserName()), User::getUserName, user.getUserName());
        lqw.eq(StringUtils.hasText(user.getPhonenumber()), User::getPhonenumber, user.getPhonenumber());
        lqw.eq(StringUtils.hasText(user.getStatus()), User::getStatus, user.getStatus());
        Page<User> userPage = new Page<>(pageNum, pageSize);
        page(userPage, lqw);
        PageVo pageVo = new PageVo(userPage.getRecords(), userPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (phoneExist(user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        List<UserRole> collect = Arrays.stream(user.getRoleIds())
                .map(menuId -> new UserRole(user.getId(), menuId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteUserById(List<Long> ids) {
        removeByIds(ids);
        userRoleService.removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfoById(Long id) {
        User user = getById(id);

        LambdaQueryWrapper<UserRole> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserRole::getUserId,id);
        List<UserRole> list = userRoleService.list(lqw);
        List<Long> collect = list.stream()
                .distinct()
                .map(userRole -> userRole.getUserId())
                .collect(Collectors.toList());


        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getDelFlag,SystemConstants.STATUS_NORMAL);
        lambdaQueryWrapper.eq(Role::getStatus,SystemConstants.STATUS_NORMAL);
        List<Role> roleList = roleService.list(lambdaQueryWrapper);
        UserVo userVo = new UserVo(user, collect, roleList);
        return ResponseResult.okResult(userVo);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(User user) {
        //删除原先数据
        userRoleService.removeById(user.getId());
        //保存用户信息
        updateById(user);
        //添加用户的角色信息
        List<UserRole> collect = Arrays.stream(user.getRoleIds())
                .map(menuId -> new UserRole(user.getId(), menuId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(collect);
        //封装返回
        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getEmail, email);
        return count(lqw) > 0;
    }
    private boolean phoneExist(String phoneNumber) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhonenumber, phoneNumber);
        return count(lqw) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getNickName, nickName);
        return count(lqw) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserName, userName);
        return count(lqw) > 0;
    }
}




