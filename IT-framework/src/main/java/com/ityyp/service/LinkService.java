package com.ityyp.service;

import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-06-27 17:32:57
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult listAllLink(Long pageNum, Long pageSize, Link link);
}
