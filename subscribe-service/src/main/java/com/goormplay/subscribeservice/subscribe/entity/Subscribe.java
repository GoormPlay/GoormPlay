package com.goormplay.subscribeservice.subscribe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    private LocalDate subscription_start_date;

    private LocalDate subscription_end_date;

    @Column(nullable = false)
    private Boolean is_cancel_scheduled;

    private String memberId;

}
