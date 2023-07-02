package com.ityyp.mapper;

import com.ityyp.domain.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
* @author Administrator
* @description 针对表【sg_comment(评论表)】的数据库操作Mapper
* @createDate 2023-06-30 09:54:17
* @Entity com.ityyp.domain.pojo.Comment
*/
@Component
public interface CommentMapper extends BaseMapper<Comment> {

}




