package com.ityyp.service.impl;

import com.ityyp.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有permissions
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission){
        if (SecurityUtils.isAdmin()){
            return true;
        }
        //否则获取当前登录用户所具有的的权限列表 如何判断是否存在permissions
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permissions);
    }
}
