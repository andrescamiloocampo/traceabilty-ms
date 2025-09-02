package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderLogDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IOrderLogRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderLogMongoAdapter implements IOrderLogPersistencePort {

    private final IOrderLogRepository orderLogRepository;
    private final IOrderLogDocumentMapper orderLogDocumentMapper;

    @Override
    public void logOrderStatusChange(OrderLogModel orderLogModel) {
        orderLogRepository.save(orderLogDocumentMapper.toOrderLogDocument(orderLogModel));
    }

    @Override
    public List<OrderLogModel> getOrderLogsByOrderId(Long orderId) {
        return orderLogDocumentMapper.toOrderLogModelList(orderLogRepository.findByOrderId(orderId));
    }


}
