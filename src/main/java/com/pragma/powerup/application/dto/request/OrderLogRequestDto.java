package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLogRequestDto {
    private Long orderId;
    private String previousState;
    private String newState;
}
