package com.sparta.post.security;

import com.sparta.post.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 사용자 권한 정보
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // UserRoleEnum role = user.getRole();
        // String authority = role.getAuthority();
        // SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("authority");
        // SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        // Collection<GrantedAuthority> authorities = new ArrayList<>();
        // authorities.add(simpleGrantedAuthority);
        // return authorities;
        return null;
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