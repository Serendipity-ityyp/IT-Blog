package com.ityyp.mapper;

import com.ityyp.domain.pojo.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2023-07-15 17:20:42
* @Entity com.ityyp.domain.pojo.ArticleTag
*/
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}




