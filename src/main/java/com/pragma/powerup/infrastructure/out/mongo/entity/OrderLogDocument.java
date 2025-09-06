package com.pragma.powerup.infrastructure.out.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "orderLogs")
public class OrderLogDocument {
    private String id;
    private Long orderId;
    private Long chefId;
    private Long customerId;
    private Long restaurantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String currentState;

    private List<OrderStatus> statusChanges;
}
