package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderLogServicePort;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

public class OrderLogUseCase implements IOrderLogServicePort {

    private final IOrderLogPersistencePort orderLogPersistencePort;

    public OrderLogUseCase(IOrderLogPersistencePort orderLogPersistencePort) {
        this.orderLogPersistencePort = orderLogPersistencePort;
    }

    @Override
    public void logOrderStatusChange(Long orderId, String status,String previousStatus) {
        OrderLogModel orderLogModel = new OrderLogModel(orderId,previousStatus,status, LocalDateTime.now());
        orderLogPersistencePort.logOrderStatusChange(orderLogModel);
    }

    @Override
    public List<OrderLogModel> getOrderLogs(Long orderId) {
        return orderLogPersistencePort.getOrderLogsByOrderId(orderId);
    }
}
