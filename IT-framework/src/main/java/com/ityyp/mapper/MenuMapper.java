package com.ityyp.mapper;

import com.ityyp.domain.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-07-14 19:55:22
* @Entity com.ityyp.domain.pojo.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




