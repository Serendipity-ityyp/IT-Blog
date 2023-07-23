package com.ityyp.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分类表
 * @TableName sg_category
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryVo implements Serializable {

    private Long id;

    /**
     * 分类名
     */
    private String name;

    private String description;

}