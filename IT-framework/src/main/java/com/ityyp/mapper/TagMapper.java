package com.ityyp.mapper;

import com.ityyp.domain.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sg_tag(标签)】的数据库操作Mapper
* @createDate 2023-07-05 10:27:18
* @Entity com.ityyp.domain.pojo.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




