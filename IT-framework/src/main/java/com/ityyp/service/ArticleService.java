package com.ityyp.service;

import com.ityyp.domain.dto.AddArticleDto;
import com.ityyp.domain.dto.ArticleListDto;
import com.ityyp.domain.dto.UpdateArticleDto;
import com.ityyp.domain.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.vo.ArticleVo;

/**
* @author Administrator
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-06-17 12:14:10
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetails(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto articleDto);

    ResponseResult listArticle(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ArticleVo getInfo(Integer id);

    ResponseResult updateArticleById(UpdateArticleDto updateArticleDto);
}
