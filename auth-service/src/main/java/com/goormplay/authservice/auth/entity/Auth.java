package com.goormplay.authservice.auth.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
@Entity
@Table(name = "auth", uniqueConstraints = @UniqueConstraint(columnNames = "memberId"))
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_index", updatable = false)
    private Long memberIndex;

    @Column(nullable = false, unique = true,  updatable = false, length = 50)
    private String username;

    @Column(nullable = false,  length = 200)
    private String password;

    @Column(nullable = false,  length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime lastLoginAt;

//    @Column(nullable = false)
//    private boolean enabled = true; 회원 탈퇴 기능
}
