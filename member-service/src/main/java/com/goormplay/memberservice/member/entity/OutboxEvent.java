package com.goormplay.memberservice.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "outboxEvent", indexes = { // 여기에 인덱스 정의
        @Index(name = "idx_outbox_processed", columnList = "processed")
})
public class OutboxEvent {
    @Id
    @Column(name = "id", length = 255)
    @NotBlank(message = "ID는 비어 있을 수 없습니다.")
    @Size(max = 255, message = "ID는 255자를 초과할 수 없습니다.")
    private String id; //UUID 문자열을 사용

    @Column(name = "aggregate_type", nullable = false, length = 255)
    @NotBlank(message = "집합체(Aggregate) 유형은 비어 있을 수 없습니다.")
    @Size(max = 255, message = "집합체 유형은 255자를 초과할 수 없습니다.")
    private String aggregateType; //"User", "Order", "Product"등 (이벤트가 관련된 주체)

    @Column(name = "aggregate_id", nullable = false, length = 255)
    @NotBlank(message = "집합체(Aggregate) ID는 비어 있을 수 없습니다.")
    @Size(max = 255, message = "집합체 ID는 255자를 초과할 수 없습니다.")
    private String aggregateId; //User ID, Order ID 등 (이벤트를 발생시킨 엔티티의 ID)

    @Column(name = "event_type", nullable = false, length = 255)
    @NotBlank(message = "이벤트 유형은 비어 있을 수 없습니다.")
    @Size(max = 255, message = "이벤트 유형은 255자를 초과할 수 없습니다.")
    private String eventType;
    @Column(name = "payload", nullable = false)
    @NotNull(message = "페이로드(Payload)는 null일 수 없습니다.")
    private String payload; // 실제 이벤트 데이터  JSON 문자열 형태로 저장

    @Column(name = "timestamp", nullable = false)
    @NotNull(message = "타임스탬프는 null일 수 없습니다.")
    private LocalDateTime timestamp;

    @Column(name = "processed", nullable = false)
    @NotNull(message = "처리 상태는 null일 수 없습니다.")
    private Boolean processed; // 이벤트가 카프카로 발행되었는지 여부를 나타내는 플래그

}
