package com.pragma.powerup.infrastructure.out.mongo.mapper;

import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.infrastructure.out.mongo.entity.OrderLogDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderLogDocumentMapper {
    OrderLogModel toOrderLogModel(OrderLogDocument orderLogDocument);
    List<OrderLogModel> toOrderLogModelList(List<OrderLogDocument> orderLogDocumentList);
    OrderLogDocument toOrderLogDocument(OrderLogModel orderLogModel);
}
