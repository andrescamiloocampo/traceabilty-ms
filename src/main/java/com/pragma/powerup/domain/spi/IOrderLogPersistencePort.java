package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderLogModel;

import java.util.List;

public interface IOrderLogPersistencePort {
    void logOrderStatusChange(OrderLogModel orderLogModel);
    List<OrderLogModel> getOrderLogsByOrderId(Long orderId);
}
