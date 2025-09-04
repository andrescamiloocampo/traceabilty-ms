package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLogModel {
    private Long orderId;
    private Long chefId;
    private Long customerId;
    private List<OrderStatusModel> statusChanges = new ArrayList<>();
}
