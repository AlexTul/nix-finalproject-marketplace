package com.tuleninov.web.model.user;

import com.tuleninov.web.model.user.response.UserUIResponse;

import java.time.OffsetDateTime;
import java.util.EnumSet;

/**
 * Class for User entity.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class CustomUserUI {

    private final Long id;

    private final String email;

    private final String nickname;

    private final UserUIStatus status;

    private final String password;

    private final OffsetDateTime createdAt;

    private final String activationCode;

    private final EnumSet<KnownAuthorityUI> authorities;

    private CustomUserUI(Long id, String email, String nickname,
                         UserUIStatus status, String password,
                         OffsetDateTime createdAt, String activationCode,
                         EnumSet<KnownAuthorityUI> authorities) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.status = status;
        this.password = password;
        this.createdAt = createdAt;
        this.activationCode = activationCode;
        this.authorities = authorities;
    }

    public static CustomUserUI create(Long id, String email, String nickname,
                                      UserUIStatus status, String password,
                                      OffsetDateTime createdAt, String activationCode,
                                      EnumSet<KnownAuthorityUI> authorities) {
        return new CustomUserUI(id, email, nickname, status,
                password, createdAt, activationCode, authorities);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public UserUIStatus getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public EnumSet<KnownAuthorityUI> getAuthorities() {
        return authorities;
    }

    public static CustomUserUI fromUserUIResponse(UserUIResponse response) {
        return new CustomUserUIBuilder()
                .id(response.id())
                .email(response.email())
                .nickname(response.nickname())
                .status(response.status())
                .password(null)
                .createdAt(response.createdAt())
                .activationCode(null)
                .authorities(response.authorities())
                .build();
    }

    public static class CustomUserUIBuilder {

        private Long id;

        private String email;

        private String nickname;

        private UserUIStatus status;

        private String password;

        private OffsetDateTime createdAt;

        private String activationCode;

        private EnumSet<KnownAuthorityUI> authorities;

        public CustomUserUIBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomUserUIBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomUserUIBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public CustomUserUIBuilder status(UserUIStatus status) {
            this.status = status;
            return this;
        }

        public CustomUserUIBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CustomUserUIBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CustomUserUIBuilder activationCode(String activationCode) {
            this.activationCode = activationCode;
            return this;
        }

        public CustomUserUIBuilder authorities(EnumSet<KnownAuthorityUI> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomUserUI build() {
            return CustomUserUI.create(id, email, nickname, status,
                    password, createdAt, activationCode, authorities);
        }
    }
}
