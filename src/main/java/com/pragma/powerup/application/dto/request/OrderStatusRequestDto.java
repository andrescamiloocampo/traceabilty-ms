package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderStatusRequestDto {
    private String previousState;
    private String newState;
    private LocalDateTime changedAt;
}
