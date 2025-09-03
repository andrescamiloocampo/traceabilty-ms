package com.pragma.powerup.infrastructure.out.mongo.mapper;

import com.pragma.powerup.domain.model.OrderStatusModel;
import com.pragma.powerup.infrastructure.out.mongo.entity.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderStatusDocumentMapper {
    OrderStatusModel toOrderStatusModel(OrderStatus orderStatus);
    List<OrderStatusModel> toOrderStatusModelList(List<OrderStatus> orderStatusList);
    OrderStatus toOrderStatusDocument(OrderStatusModel orderStatusModel);
}
