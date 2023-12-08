package com.ityyp.job;

import com.ityyp.domain.pojo.Article;
import com.ityyp.service.ArticleService;
import com.ityyp.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;


    @Scheduled(cron = "0 0/10 * * * ?")//表示每10分钟执行一次任务， 0 0/2 * * * ?    表示每2分钟 执行任务
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        for (Article article : articles) {
            article.setUpdateBy(null);
            articleService.updateById(article);
        }

//        articles.stream()
//                .map(article -> articleService.updateArticleViewCountById(article))
//                .collect(Collectors.toList());
    }
}
