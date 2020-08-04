package com.github.jamie9504.board.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of permissions (name)
        for (String permission : this.user.getPermissionList()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }

        // Extract roles (ROLE_name)
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRoleName());
        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    public String getNickname() {
        return this.user.getNickname();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
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
        return this.user.getUsed() == 1;
    }
}
