package com.example.vuesecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author 张乔
 * @Date 2024/2/26 19:04
 */

@Data
public class MyUserDetail implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    private Users Users;

    //    角色
    private Set<String> roles;

    //    权限
    private Set<String> permissions;


    @JsonIgnore  //json忽略
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<>();

//        如果角色不用空，则将角色添加到list中
        if (!ObjectUtils.isEmpty(roles)) {
            roles.forEach(role -> list.add(new SimpleGrantedAuthority(role)));
        }

//                如果权限不用空，则将权限添加到list中
        if (!ObjectUtils.isEmpty(permissions)) {
            permissions.forEach(permission -> list.add(new SimpleGrantedAuthority(permission)));
        }
        return list;

    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.getUsers().getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getUsers().getUsername();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.getUsers().getStatus() == 0;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.getUsers().getStatus() == 0;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.getUsers().getStatus() == 0;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.getUsers().getStatus() == 0;
    }
}
