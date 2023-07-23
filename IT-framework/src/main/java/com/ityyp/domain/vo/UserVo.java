package com.ityyp.domain.vo;

import com.ityyp.domain.pojo.Role;
import com.ityyp.domain.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private User user;
    private List<Long> roleIds;
    private List<Role> roles;
}
