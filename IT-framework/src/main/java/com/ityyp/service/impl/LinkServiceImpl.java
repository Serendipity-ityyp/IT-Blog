package com.ityyp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ityyp.constants.SystemConstants;
import com.ityyp.domain.ResponseResult;
import com.ityyp.domain.pojo.Link;
import com.ityyp.domain.vo.LinkVo;
import com.ityyp.domain.vo.PageVo;
import com.ityyp.service.LinkService;
import com.ityyp.mapper.LinkMapper;
import com.ityyp.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2023-06-27 17:32:57
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);
        List<Link> list = list(lqw);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        System.out.println();
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult listAllLink(Long pageNum, Long pageSize, Link link) {
        LambdaQueryWrapper<Link> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(link.getName()),Link::getName,link.getName());
        lqw.eq(StringUtils.hasText(link.getStatus()),Link::getStatus, link.getStatus());

        Page<Link> linkPage = new Page<>(pageNum,pageSize);
        page(linkPage,lqw);
        PageVo pageVo = new PageVo(linkPage.getRecords(), linkPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}

