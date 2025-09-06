package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderLogResponseMapper {
    OrderLogResponseDto toResponse(OrderLogModel orderLogModel);

    List<OrderLogResponseDto> toResponseList(List<OrderLogModel> orderLogModels);

    @Mapping(target = "totalTime", source = "totalMinutes", qualifiedByName = "formatMinutesToTime")
    EmployeeEfficiencyResponseDto toEmployeeEfficiencyResponseDto(OrderEfficiencyModel dto);

    List<EmployeeEfficiencyResponseDto> toEmployeeEfficiencyResponseDtoList(List<OrderEfficiencyModel> dtos);

    @Named("formatMinutesToTime")
    default String formatMinutesToTime(BigInteger totalMinutes) {
        if (totalMinutes == null) {
            return "0 seconds";
        }

        Duration duration = Duration.ofMinutes(totalMinutes.longValue());

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(" hour");
            if (hours > 1) result.append("s");
        }
        if (minutes > 0) {
            if (result.length() > 0) result.append(" ");
            result.append(minutes).append(" minute");
            if (minutes > 1) result.append("s");
        }
        if (seconds > 0 || result.length() == 0) {
            if (result.length() > 0) result.append(" ");
            result.append(seconds).append(" second");
            if (seconds != 1) result.append("s");
        }

        return result.toString();
    }
}