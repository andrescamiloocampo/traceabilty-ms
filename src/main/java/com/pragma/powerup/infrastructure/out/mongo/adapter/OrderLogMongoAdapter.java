package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.infrastructure.out.mongo.entity.OrderLogDocument;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderLogDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderStatusDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IOrderLogRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class OrderLogMongoAdapter implements IOrderLogPersistencePort {

    private final IOrderLogRepository orderLogRepository;
    private final IOrderLogDocumentMapper orderLogDocumentMapper;
    private final IOrderStatusDocumentMapper orderStatusDocumentMapper;

    @Override
    public void logOrderStatusChange(Long orderId, Long chefId, Long restaurantId, Long customerId, OrderStatusModel orderStatusModel) {
        OrderLogDocument log = orderLogRepository.findByOrderId(orderId);

        if (log == null) {
            OrderLogModel newLogModel = new OrderLogModel();
            newLogModel.setOrderId(orderId);
            newLogModel.setChefId(chefId);
            newLogModel.setRestaurantId(restaurantId);
            newLogModel.setCustomerId(customerId);
            newLogModel.getStatusChanges().add(orderStatusModel);

            log = orderLogDocumentMapper.toOrderLogDocument(newLogModel);
            log.setCreatedAt(orderStatusModel.getChangedAt());
            log.setUpdatedAt(orderStatusModel.getChangedAt());
            log.setCurrentState(orderStatusModel.getNewState());
        } else {
            log.setChefId(chefId);
            log.setUpdatedAt(orderStatusModel.getChangedAt());
            log.setCurrentState(orderStatusModel.getNewState());
            log.getStatusChanges().add(orderStatusDocumentMapper.toOrderStatusDocument(orderStatusModel));
        }

        orderLogRepository.save(log);
    }

    @Override
    public OrderLogModel getOrderLogByOrderId(Long orderId) {
        return orderLogDocumentMapper.toOrderLogModel(orderLogRepository.findByOrderId(orderId));
    }

    @Override
    public List<OrderLogModel> getOrderLogsByCustomerId(Long customerId) {
        return orderLogDocumentMapper.toOrderLogModelList(orderLogRepository.findByCustomerId(customerId));
    }

    @Override
    public List<OrderEfficiencyModel> getOrderEfficiencyReport(Long restaurantId) {
        return orderLogDocumentMapper.toOrderEfficiencyModelList(orderLogRepository.findByRestaurantIdAndCurrentState(restaurantId, "DELIVERED"));
    }

    @Override
    public List<OrderLogModel> getOrdersByChefId(Long employeeId) {
        return orderLogDocumentMapper.toOrderLogModelList(orderLogRepository.findByChefId(employeeId));
    }

}
