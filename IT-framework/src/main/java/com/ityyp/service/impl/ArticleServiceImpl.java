package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.constants.SystemConstants;
import com.ityyp.domain.pojo.Article;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Category;
import com.ityyp.domain.pojo.PageVo;
import com.ityyp.domain.vo.ArticleDetailsVo;
import com.ityyp.domain.vo.ArticleListVo;
import com.ityyp.domain.vo.HotArticleVo;
import com.ityyp.service.ArticleService;
import com.ityyp.mapper.ArticleMapper;
import com.ityyp.service.CategoryService;
import com.ityyp.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2023-06-17 12:14:10
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        //必须是正式文章
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        lqw.orderByDesc(Article::getViewCount);
        //最多查询10条
        Page<Article> page = new Page<>(SystemConstants.HOTARTICLE_CURRENT_PAGE,SystemConstants.HOTARTICLE_CURRENT_PAGESIZE);
        page(page,lqw);
        List<Article> articles = page.getRecords();
        //bean拷贝
/*        List<HotArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article,vo);
            articleVos.add(vo);
        }*/

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);

    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        //如果有categoryId就要查询时和传入的相同
        lqw.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        lqw.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lqw.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lqw);
        List<Article> articles = page.getRecords();
        //查询categoryName
        articles = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
//                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetails(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //转换成Vo
        ArticleDetailsVo articleDetailsVo = BeanCopyUtils.copyBean(article, ArticleDetailsVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailsVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            articleDetailsVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailsVo);
    }
}




