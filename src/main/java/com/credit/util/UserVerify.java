package com.credit.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerify implements UserDetails {
    private Integer id;
    private String usercode;
    private String password;
    private String username;
    private String faculty;
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    public UserVerify(Integer id, String usercode, String password, String username, String faculty, List<GrantedAuthority> authorities) {
        this.id = id;
        this.usercode = usercode;
        this.password = password;
        this.username = username;
        this.faculty = faculty;
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
