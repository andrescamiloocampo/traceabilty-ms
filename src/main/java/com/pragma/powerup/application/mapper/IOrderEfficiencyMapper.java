package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEfficiencyMapper {
    OrderEfficiencyResponseDto toResponse(OrderEfficiencyModel orderEfficiencyModel);
    List<OrderEfficiencyResponseDto> toResponseList(List<OrderEfficiencyModel> orderEfficiencyModels);
}
