package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderLogModel;

import java.util.List;

public interface IOrderLogServicePort {
    void logOrderStatusChange(Long orderId, String status, String previousStatus);
    List<OrderLogModel> getOrderLogs(Long orderId);
}
