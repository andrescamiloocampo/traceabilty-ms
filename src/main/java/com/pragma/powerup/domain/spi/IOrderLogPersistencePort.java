package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;

public interface IOrderLogPersistencePort {
    void logOrderStatusChange(Long orderId, Long chefId, Long customerId, OrderStatusModel orderStatusModel);
    OrderLogModel getOrderLogsByOrderId(Long orderId);
}
