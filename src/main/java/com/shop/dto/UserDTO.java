package com.shop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements UserDetails, OAuth2User {
    @NotBlank
    @Length(min = 4, max = 15)
    private String id;
    @NotBlank
    @Length(min = 88)
    private String ci;
    @NotBlank(message = "password는 4-8자리여야함!")
    @Length(min = 4, max = 8)
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}")
    private String tel;
    @Valid
    @NotNull
    private ImageFileDTO imageFile;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    private String token;

    private SnsInfoDTO snsInfo;

    @Override
    public Map<String, Object> getAttributes() {
        return this.snsInfo.getAttributes();
    }

    @Override
    public String getName() {
        return this.snsInfo.getClientName();
    }


    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}