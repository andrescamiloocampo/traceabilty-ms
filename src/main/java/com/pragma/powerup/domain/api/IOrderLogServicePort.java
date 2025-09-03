package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderLogModel;


public interface IOrderLogServicePort {
    void logOrderStatusChange(OrderLogModel orderLogModel);
    OrderLogModel getOrderLogs(Long orderId);
}
