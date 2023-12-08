package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.constants.SystemConstants;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.dto.AddArticleDto;
import com.ityyp.domain.dto.ArticleListDto;
import com.ityyp.domain.dto.UpdateArticleDto;
import com.ityyp.domain.pojo.Article;
import com.ityyp.domain.pojo.ArticleTag;
import com.ityyp.domain.pojo.Category;
import com.ityyp.domain.vo.*;
import com.ityyp.mapper.ArticleMapper;
import com.ityyp.service.ArticleService;
import com.ityyp.service.ArticleTagService;
import com.ityyp.service.CategoryService;
import com.ityyp.utils.BeanCopyUtils;
import com.ityyp.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        implements ArticleService {

    @Autowired
    CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        //必须是正式文章
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        lqw.orderByDesc(Article::getViewCount);
        //最多查询10条
        Page<Article> page = new Page<>(SystemConstants.HOTARTICLE_CURRENT_PAGE, SystemConstants.HOTARTICLE_CURRENT_PAGESIZE);
        page(page, lqw);
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
        lqw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //状态是正式发布的
        lqw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lqw.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lqw);
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
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成Vo
        ArticleDetailsVo articleDetailsVo = BeanCopyUtils.copyBean(article, ArticleDetailsVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailsVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailsVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailsVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(AddArticleDto articleDto) {
        //添加博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(articleListDto.getSummary()), Article::getSummary, articleListDto.getSummary());
        lqw.like(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle, articleListDto.getTitle());
        lqw.eq(Article::getDelFlag, SystemConstants.STATUS_NORMAL);

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lqw);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ArticleVo getInfo(Integer id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> list = articleTagService.list(lqw);
        List<Long> collect = list.stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        articleVo.setTags(collect);
        return articleVo;
    }

    @Override
    public ResponseResult updateArticleById(UpdateArticleDto updateArticleDto) {
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);
        /**
         * 更新博客关联标签信息
         */
        //移除之前标签信息
        LambdaQueryWrapper<ArticleTag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleTag::getArticleId,updateArticleDto.getId());
        articleTagService.remove(lqw);
        //添加标签信息
        List<Long> categoryId = updateArticleDto.getTags();

        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tag -> new ArticleTag(updateArticleDto.getId(), tag))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public Object updateArticleViewCountById(Article article) {
        return getBaseMapper().updateArticleViewCountById(article);
    }
}




