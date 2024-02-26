package com.example.vuesecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author 张乔
 * @Date 2024/2/26 19:04
 */

@Data
public class MyTUserDetail implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    private Users Users;

    @JsonIgnore  //json忽略
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
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
        return this.getUsers().getStatus()==0;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.getUsers().getStatus()==0;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.getUsers().getStatus()==0;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.getUsers().getStatus()==0;
    }
}
