package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.infrastructure.out.mongo.entity.OrderLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IOrderLogRepository extends MongoRepository<OrderLogDocument, String> {
    OrderLogDocument findByOrderId(Long orderId);
    List<OrderLogDocument> findByCustomerId(Long customerId);
}
