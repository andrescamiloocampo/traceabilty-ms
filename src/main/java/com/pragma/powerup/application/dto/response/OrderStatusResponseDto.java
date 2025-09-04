package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderStatusResponseDto {
    private String previousState;
    private String newState;
    private LocalDateTime changedAt;
}
