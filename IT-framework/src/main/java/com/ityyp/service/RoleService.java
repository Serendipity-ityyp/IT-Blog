package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.dto.AddRoleDto;
import com.ityyp.domain.dto.ChangeRoleStatusDto;
import com.ityyp.domain.dto.ListRoleDto;
import com.ityyp.domain.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ityyp.domain.vo.UpdateRoleDto;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-07-14 20:03:27
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listAllRole(Integer pageNum, Integer pageSize, ListRoleDto listRoleDto);

    ResponseResult changeStatus(ChangeRoleStatusDto changeRoleStatusDto);

    ResponseResult addRole(Role role);

    ResponseResult getRoleById(Integer id);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult listAllRoles();
}
