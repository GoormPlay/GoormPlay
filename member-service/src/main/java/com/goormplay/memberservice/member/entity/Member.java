package com.goormplay.memberservice.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(columnNames = "username"),
        indexes = {
                @Index(name = "idx_member_username", columnList = "username") // username 필드에 인덱스 추가
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column(name = "id", updatable = false, columnDefinition = "CHAR(36)")
    @NotBlank(message = "회원 ID는 비어 있을 수 없음.")
    @Size(max = 36, message = "회원 ID는 36자를 초과할 수 없음.")
    private String id;

    @Column(name = "username", nullable = false, unique = true, updatable = false, length = 50)
    @NotBlank(message = "사용자 이름은 비어 있을 수 없음.")
    @Size(min = 4, max = 50, message = "사용자 이름은 4자 이상 50자 이하.")
    private String username;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull(message = "생성일시는 null일 수 없음.")
    private LocalDateTime createdAt;

    @Column(name = "gender", length = 10)
    @NotNull(message = "성별은 null일 수 없음.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "age")
    @Min(value = 0, message = "나이는 0보다 작을 수 없음.")
    @Max(value = 150, message = "나이는 150보다 클 수 없음.")
    private Integer age;

}