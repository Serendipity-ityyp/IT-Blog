package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

/**
* @author Administrator
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2023-06-27 10:06:11
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult listCategory(Integer pageNum,Integer pageSize,Category category);
}
