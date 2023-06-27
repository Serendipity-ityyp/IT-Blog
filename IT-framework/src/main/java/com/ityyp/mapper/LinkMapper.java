package com.ityyp.mapper;

import com.ityyp.domain.pojo.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sg_link(友链)】的数据库操作Mapper
* @createDate 2023-06-27 17:32:57
* @Entity com.ityyp.domain.pojo.Link
*/
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}




