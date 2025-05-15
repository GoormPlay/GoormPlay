package com.goormplay.adadminservice.entity;

import com.mongodb.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ad_transaction_log")
public class AdTransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String adInstanceId;

    private String adSnId;

    private String type; // A, B

    private String userId;

    private String eventType; // "impression" or "click"

    private Long cost;

    private LocalDateTime timestamp;
}
