package com.ityyp.mapper;

import com.ityyp.domain.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sg_category(分类表)】的数据库操作Mapper
* @createDate 2023-06-27 10:06:11
* @Entity com.ityyp.domain.pojo.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




