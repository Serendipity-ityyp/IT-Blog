package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.constants.SystemConstants;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.dto.AddRoleDto;
import com.ityyp.domain.dto.ChangeRoleStatusDto;
import com.ityyp.domain.dto.ListRoleDto;
import com.ityyp.domain.pojo.Menu;
import com.ityyp.domain.pojo.Role;
import com.ityyp.domain.pojo.RoleMenu;
import com.ityyp.domain.vo.PageVo;
import com.ityyp.domain.vo.RoleListVo;
import com.ityyp.domain.vo.RoleVo;
import com.ityyp.domain.vo.UpdateRoleDto;
import com.ityyp.service.MenuService;
import com.ityyp.service.RoleMenuService;
import com.ityyp.service.RoleService;
import com.ityyp.mapper.RoleMapper;
import com.ityyp.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2023-07-14 20:03:27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {
   @Autowired
   private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是不是管理员 如果是返回集合中只需要admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //如果不是查询用户所具有的的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult listAllRole(Integer pageNum, Integer pageSize, ListRoleDto listRoleDto) {
        //查询条件
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.hasText(listRoleDto.getStatus()), Role::getStatus, listRoleDto.getStatus());
        lqw.like(StringUtils.hasText(listRoleDto.getRoleName()), Role::getRoleName, listRoleDto.getRoleName());
        lqw.orderByAsc(Role::getRoleSort);
        //分页查询
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, lqw);
        //封装返回
        List<Role> roleList = page.getRecords();
        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeanList(roleList, RoleListVo.class);
        PageVo pageVo = new PageVo(roleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        Role role = new Role();
        role.setStatus(changeRoleStatusDto.getStatus());
        role.setId(changeRoleStatusDto.getRoleId());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(Role role) {
        save(role);
        List<RoleMenu> collect = role.getMenuIds().stream()
                .map(id -> new RoleMenu(role.getId(), id))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Integer id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        //封装roleMenu对象
        Long roleId = updateRoleDto.getId();
        List<RoleMenu> collect = Arrays.stream(updateRoleDto.getMenuIds())
                .map(menuId-> new RoleMenu(roleId,menuId))
                .collect(Collectors.toList());
        //删除原有rolemenu数据并保存新的数据
        roleMenuService.removeById(updateRoleDto.getId());
        roleMenuService.saveBatch(collect);

        //更改role信息
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRoles() {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Role::getStatus,SystemConstants.STATUS_NORMAL);
        lqw.eq(Role::getDelFlag,SystemConstants.STATUS_NORMAL);
        List<Role> list = list(lqw);
        return ResponseResult.okResult(list);
    }
}




