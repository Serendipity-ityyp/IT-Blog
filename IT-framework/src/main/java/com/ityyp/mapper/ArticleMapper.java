package com.ityyp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ityyp.domain.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sg_article(文章表)】的数据库操作Mapper
* @createDate 2023-06-17 12:14:10
* @Entity com.ityyp.domain.pojo.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    Object updateArticleViewCountById(Article article);
}




