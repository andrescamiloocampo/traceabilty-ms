package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderLogRequestDto {
    private Long orderId;
    private Long chefId;
    private Long customerId;
    private List<OrderStatusRequestDto> statusChanges;
}
