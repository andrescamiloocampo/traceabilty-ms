package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderLogModel;

import java.util.List;


public interface IOrderLogServicePort {
    void logOrderStatusChange(OrderLogModel orderLogModel);
    OrderLogModel getOrderLogs(Long orderId);
    List<OrderLogModel> getAllOrderLogs(Long customerId);
}
