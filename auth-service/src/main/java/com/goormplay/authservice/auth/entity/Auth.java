package com.goormplay.authservice.auth.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
@Entity
@Table(name = "auth", uniqueConstraints = @UniqueConstraint(columnNames = "username"),
        indexes = {
                @Index(name = "idx_username", columnList = "username")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {

    @Id
    @Column(name = "id", updatable = false, columnDefinition = "CHAR(36)")
    @NotBlank(message = "인증 ID는 비어 있을 수 없음.")
    @Size(max = 36, message = "인증 ID는 36자를 초과할 수 없음.")
    private String id;

    @Column(name = "username", nullable = false, unique = true, updatable = false, length = 50)
    @NotBlank(message = "사용자 이름은 비어 있을 수 없음.")
    @Size(min = 4, max = 50, message = "사용자 이름은 4자 이상 50자 이하.")
    private String username;

    @Column(name = "password", nullable = false, length = 200)
    @NotBlank(message = "비밀번호는 비어 있을 수 없음.")
    @Size(min = 8, max = 200, message = "비밀번호는 최소 8자 이상.")
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    @NotNull(message = "역할은 null일 수 없음.")
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull(message = "생성일시는 null일 수 없음.")
    private LocalDateTime createdAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
}