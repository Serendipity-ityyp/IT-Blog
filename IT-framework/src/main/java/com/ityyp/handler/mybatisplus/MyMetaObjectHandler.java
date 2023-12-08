package com.ityyp.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ityyp.domain.pojo.LoginUser;
import com.ityyp.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        Long userId = (Long) metaObject.getValue("updateBy");
//        System.out.println(userId);
//        if (userId.equals("") || userId.equals(null)) {
//
//        }

/*        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
//                e.printStackTrace();
            userId = -1L;//表示是自己创建
        }*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId =null;
        if (authentication!=null) {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            userId = loginUser.getUser().getId();
        }
        this.setFieldValByName("updateTime", new Date(), metaObject);
//        this.setFieldValByName("updateBy", userId, metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }
}