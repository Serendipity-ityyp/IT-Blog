package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.constants.SystemConstants;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Menu;
import com.ityyp.domain.pojo.Role;
import com.ityyp.domain.pojo.RoleMenu;
import com.ityyp.domain.vo.MenuListVo;
import com.ityyp.domain.vo.MenuTreeVo;
import com.ityyp.domain.vo.RoleMenuTreeselectVo;
import com.ityyp.domain.vo.UpdateRoleDto;
import com.ityyp.service.MenuService;
import com.ityyp.mapper.MenuMapper;
import com.ityyp.service.RoleMenuService;
import com.ityyp.utils.BeanCopyUtils;
import com.ityyp.utils.SecurityUtils;
import com.ityyp.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2023-07-14 19:55:22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有权限
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
            lqw.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            lqw.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(lqw);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            //返回所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            //否则 返回当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建menuTree
        List<Menu> menuTree = buildMenuTree(menus, 0L);
        return menuTree;
    }

    @Override
    public ResponseResult listAllMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.hasText(status), Menu::getStatus, status);
        lqw.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        lqw.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> list = list(lqw);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult selectArticleById(Integer id) {
        Menu menu = getById(id);
        MenuListVo menuListVo = BeanCopyUtils.copyBean(menu, MenuListVo.class);
        return ResponseResult.okResult(menuListVo);
    }

    @Override
    public ResponseResult deleteMenuById(Integer id) {
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Menu::getParentId, id);
        List<Menu> list = list(lqw);
        if (!list.isEmpty()) {
            return ResponseResult.errorResult(500, "存在子菜单不允许删除");
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<Menu> selectMenuList() {
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
        lqw.orderByAsc(Menu::getOrderNum, Menu::getParentId);
        return list(lqw);
    }

    @Override
    public ResponseResult roleMenuTreeselect(Integer id) {

        List<Menu> menus = selectMenuList();
        List<MenuTreeVo> options = SystemConverter.buildMenuSelectTree(menus);

        LambdaQueryWrapper<RoleMenu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(RoleMenu::getRoleId, id);
        List<RoleMenu> list = roleMenuService.list(lqw);
        List<Long> collect = list.stream()
                .map(roleMenu -> roleMenu.getMenuId())
                .collect(Collectors.toList());
        RoleMenuTreeselectVo roleMenuTreeselectVo = new RoleMenuTreeselectVo(options, collect);
        return ResponseResult.okResult(roleMenuTreeselectVo);
    }




    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
        return options;

    }

    private List<Menu> buildMenuTree(List<Menu> menus, long parentId) {
        List<Menu> menuList = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuList;
    }

    /**
     * 获取存入参数的 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}




