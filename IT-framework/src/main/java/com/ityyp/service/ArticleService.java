package com.ityyp.service;

import com.ityyp.domain.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ityyp.domain.ResponseResult;

/**
* @author Administrator
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-06-17 12:14:10
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetails(Long id);
}
