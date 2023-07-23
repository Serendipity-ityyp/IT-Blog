package com.ityyp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.dto.AddRoleDto;
import com.ityyp.domain.dto.ChangeRoleStatusDto;
import com.ityyp.domain.dto.ListRoleDto;
import com.ityyp.domain.pojo.Role;
import com.ityyp.domain.vo.UpdateRoleDto;
import com.ityyp.service.RoleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult listAllRole(Integer pageNum, Integer pageSize, ListRoleDto listRoleDto){
       return roleService.listAllRole(pageNum,pageSize,listRoleDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto){
        return roleService.changeStatus(changeRoleStatusDto);
    }

    @PostMapping()
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable Integer id){
        return roleService.getRoleById(id);
    }

    @PutMapping()
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto){
        return roleService.updateRole(updateRoleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRoleById(@PathVariable Integer id){
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRoles(){
        return roleService.listAllRoles();
    }


}
