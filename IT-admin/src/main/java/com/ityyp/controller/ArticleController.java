package com.ityyp.controller;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.dto.AddArticleDto;
import com.ityyp.domain.dto.ArticleListDto;
import com.ityyp.domain.dto.UpdateArticleDto;
import com.ityyp.domain.vo.ArticleVo;
import com.ityyp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto articleDto){
        return articleService.add(articleDto);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, ArticleListDto articleListDto ){
        return articleService.listArticle(pageNum,pageSize,articleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult listArticleById(@PathVariable Integer id){
        ArticleVo articleVo = articleService.getInfo(id);
        return ResponseResult.okResult(articleVo);
    }

    @PutMapping
    public ResponseResult updateArticleById(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.updateArticleById(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Integer id){
        articleService.removeById(id);
        return  ResponseResult.okResult();
    }
}
