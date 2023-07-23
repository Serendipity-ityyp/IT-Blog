package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ityyp.domain.vo.UpdateRoleDto;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-07-14 19:55:22
*/
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult listAllMenu(String status, String menuName);

    ResponseResult selectArticleById(Integer id);

    ResponseResult deleteMenuById(Integer id);

    List<Menu> selectMenuList();

    ResponseResult roleMenuTreeselect(Integer id);

}
