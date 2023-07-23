package com.ityyp.controller;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Menu;
import com.ityyp.domain.vo.MenuTreeVo;
import com.ityyp.domain.vo.UpdateRoleDto;
import com.ityyp.service.MenuService;
import com.ityyp.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult listAllMenu(String status,String menuName){
        return menuService.listAllMenu(status,menuName);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult selectArticleById(@PathVariable Integer id){
        return menuService.selectArticleById(id);
    }

    @PutMapping
    public ResponseResult editMenu(@RequestBody Menu menu){
        if (menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(500,"修改菜单'"+menu.getMenuName()+"'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMenuById(@PathVariable Integer id){
        return menuService.deleteMenuById(id);
    }

    /**
     * 需认证研究
     * @return
     */
    // TODO 需认证研究背会！！！
    @GetMapping("/treeselect")
    public ResponseResult treeselect(){
        List<Menu> menus = menuService.selectMenuList();
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    @GetMapping("roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable Integer id){
        return menuService.roleMenuTreeselect(id);
    }


}

