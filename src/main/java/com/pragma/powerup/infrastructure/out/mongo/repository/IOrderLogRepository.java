package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.infrastructure.out.mongo.entity.OrderLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IOrderLogRepository extends MongoRepository<OrderLogDocument, String> {
    OrderLogDocument findByOrderId(Long orderId);
}
