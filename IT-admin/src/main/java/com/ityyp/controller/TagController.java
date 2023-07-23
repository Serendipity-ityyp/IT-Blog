package com.ityyp.controller;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.dto.TagListDto;
import com.ityyp.domain.pojo.Tag;
import com.ityyp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }
//    @PreAuthorize()
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTagById(@PathVariable Integer id) {
        return tagService.deleteTagById(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Integer id) {
        return ResponseResult.okResult(tagService.getById(id));
    }

    @PutMapping
    public ResponseResult updateTagById(@RequestBody Tag tag){
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

}
