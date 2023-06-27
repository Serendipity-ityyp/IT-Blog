package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.constants.SystemConstants;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Article;
import com.ityyp.domain.pojo.Category;
import com.ityyp.domain.vo.CategoryVo;
import com.ityyp.service.ArticleService;
import com.ityyp.service.CategoryService;
import com.ityyp.mapper.CategoryMapper;
import com.ityyp.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2023-06-27 10:06:11
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表状态为已发布的文章
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(lqw);
        //获取文章的id，并且去重
        List<Long> collect = articleList.stream()
                .map(article -> article.getCategoryId())
                .distinct()
                .collect(Collectors.toList());
        //查询分类表
        List<Category> categories = listByIds(collect);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}




