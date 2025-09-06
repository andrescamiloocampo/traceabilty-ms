package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;

import java.util.List;

public interface IOrderLogPersistencePort {
    void logOrderStatusChange(Long orderId, Long chefId,Long restaurantId, Long customerId, OrderStatusModel orderStatusModel);
    OrderLogModel getOrderLogByOrderId(Long orderId);
    List<OrderLogModel> getOrderLogsByCustomerId(Long customerId);
    List<OrderEfficiencyModel> getOrderEfficiencyReport(Long restaurantId);
    List<OrderLogModel> getOrdersByChefId(Long employeeId);
}
