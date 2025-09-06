package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderLogServicePort;
import com.pragma.powerup.domain.exception.InvalidOwnerException;
import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantOwnerPersistencePort;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class OrderLogUseCase implements IOrderLogServicePort {

    private final IOrderLogPersistencePort orderLogPersistencePort;
    private final IRestaurantOwnerPersistencePort restaurantOwnerPersistencePort;

    public OrderLogUseCase(IOrderLogPersistencePort orderLogPersistencePort, IRestaurantOwnerPersistencePort restaurantOwnerPersistencePort) {
        this.orderLogPersistencePort = orderLogPersistencePort;
        this.restaurantOwnerPersistencePort = restaurantOwnerPersistencePort;
    }

    @Override
    public void logOrderStatusChange(OrderLogModel orderLogModel) {
        orderLogPersistencePort.logOrderStatusChange(orderLogModel.getOrderId(),
                orderLogModel.getChefId(),
                orderLogModel.getRestaurantId(),
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

    @Override
    public List<OrderEfficiencyModel> getOrderEfficiencyReport(long ownerId, long restaurantId) {
        boolean ownership = restaurantOwnerPersistencePort.getOwnership((int) restaurantId, (int) ownerId);

        if (!ownership) {
            throw new InvalidOwnerException();
        }

        return orderLogPersistencePort.getOrderEfficiencyReport(restaurantId);
    }

    @Override
    public OrderEfficiencyModel getOrderEfficiencyByEmployee(Long employeeId, Long restaurantId, Long ownerId) {
        List<OrderLogModel> orders = orderLogPersistencePort.getOrdersByChefId(employeeId);
        OrderEfficiencyModel efficiency = new OrderEfficiencyModel();
        int size = orders.size();

        boolean ownership = restaurantOwnerPersistencePort.getOwnership(restaurantId.intValue(), ownerId.intValue());
        if (!ownership) {
            throw new InvalidOwnerException();
        }

        if (size == 0) {
            efficiency.setChefId(employeeId);
            efficiency.setTotalMinutes(BigInteger.ZERO);
            return efficiency;
        }

        BigInteger totalMinutes = orders.stream()
                .map(order -> {
                    LocalDateTime start = order.getCreatedAt();
                    LocalDateTime end = order.getUpdatedAt();
                    if (start == null || end == null) {
                        return BigInteger.ZERO;
                    }
                    return BigInteger.valueOf(Duration.between(start, end).toMinutes());
                })
                .reduce(BigInteger.ZERO, BigInteger::add);

        BigInteger divisor = BigInteger.valueOf(size);
        BigInteger[] divAndRem = totalMinutes.divideAndRemainder(divisor);

        BigInteger avg = divAndRem[0];
        if (divAndRem[1].multiply(BigInteger.TWO).compareTo(divisor) >= 0) {
            avg = avg.add(BigInteger.ONE);
        }

        efficiency.setChefId(employeeId);
        efficiency.setTotalMinutes(avg);

        return efficiency;
    }



}
