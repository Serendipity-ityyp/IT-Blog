package com.ityyp.controller;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Link;
import com.ityyp.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult listAllLink(Long pageNum,Long pageSize,Link link){
        return linkService.listAllLink(pageNum,pageSize,link);
    }

    @PostMapping()
    public ResponseResult addLink(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Integer id){
        return ResponseResult.okResult(linkService.getById(id));
    }

    @PutMapping()
    public ResponseResult updateLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLinkById(@PathVariable Integer id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
