package com.ityyp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.domain.pojo.RoleMenu;
import com.ityyp.service.RoleMenuService;
import com.ityyp.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2023-07-17 13:52:22
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




