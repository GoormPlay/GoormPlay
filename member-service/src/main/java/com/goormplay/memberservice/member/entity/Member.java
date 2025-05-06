package com.goormplay.memberservice.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_index", updatable = false)
    private Long memberIndex;

    @Column(nullable = false, unique = true, updatable = false, length = 50)
    private String username;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private Boolean isSubcribe;

    @Column
    private Boolean isCancelScheduled ;

    @Column(length = 10)
    @Enumerated
    private Gender gender;

    @Column
    private Integer age;


//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    //Spring MVC에서 요청/응답 변환 시 적용, DB에 저장되는 실제 값의 포맷은 DB 설정에 따름
//    private LocalDate birthDate;

}