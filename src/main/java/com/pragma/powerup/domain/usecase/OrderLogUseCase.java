package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderLogServicePort;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;

import java.util.List;

public class OrderLogUseCase implements IOrderLogServicePort {

    private final IOrderLogPersistencePort orderLogPersistencePort;

    public OrderLogUseCase(IOrderLogPersistencePort orderLogPersistencePort) {
        this.orderLogPersistencePort = orderLogPersistencePort;
    }

    @Override
    public void logOrderStatusChange(OrderLogModel orderLogModel) {
        orderLogPersistencePort.logOrderStatusChange(orderLogModel.getOrderId(),
                orderLogModel.getChefId(),
                orderLogModel.getCustomerId(),
                new OrderStatusModel(
                        orderLogModel.getStatusChanges().get(orderLogModel.getStatusChanges().size() - 1).getPreviousState(),
                        orderLogModel.getStatusChanges().get(orderLogModel.getStatusChanges().size() - 1).getNewState(),
                        orderLogModel.getStatusChanges().get(orderLogModel.getStatusChanges().size() - 1).getChangedAt()
                ));
    }

    @Override
    public OrderLogModel getOrderLogs(Long orderId) {
        return orderLogPersistencePort.getOrderLogByOrderId(orderId);
    }

    @Override
    public List<OrderLogModel> getAllOrderLogs(Long customerId) {
        return orderLogPersistencePort.getOrderLogsByCustomerId(customerId);
    }
}
