package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderLogResponseDto {
    private Long orderId;
    private Long chefId;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String currentState;
    private List<OrderStatusResponseDto> statusChanges;
}
