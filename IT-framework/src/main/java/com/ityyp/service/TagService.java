package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.dto.TagListDto;
import com.ityyp.domain.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2023-07-05 10:27:18
*/
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTagById(Integer id);

    ResponseResult listAllTag();
}
