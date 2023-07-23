package com.ityyp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.domain.pojo.ArticleTag;
import com.ityyp.service.ArticleTagService;
import com.ityyp.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-07-15 17:20:42
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




