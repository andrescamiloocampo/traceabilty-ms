package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;

import java.util.List;

public interface IOrderLogPersistencePort {
    void logOrderStatusChange(Long orderId, Long chefId, Long customerId, OrderStatusModel orderStatusModel);
    OrderLogModel getOrderLogByOrderId(Long orderId);
    List<OrderLogModel> getOrderLogsByCustomerId(Long customerId);
}
