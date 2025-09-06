package com.pragma.powerup.infrastructure.out.mongo.mapper;

import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.infrastructure.out.mongo.entity.OrderLogDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderLogDocumentMapper {
    OrderLogModel toOrderLogModel(OrderLogDocument orderLogDocument);

    List<OrderLogModel> toOrderLogModelList(List<OrderLogDocument> orderLogDocumentList);

    OrderLogDocument toOrderLogDocument(OrderLogModel orderLogModel);

    @Mapping(target = "totalTime", source = "orderLogDocument", qualifiedByName = "calculateTotalTime")
    @Mapping(target = "totalMinutes", source = "orderLogDocument", qualifiedByName = "calculateTotalMinutes")
    @Mapping(target = "id", source = "orderId")
    OrderEfficiencyModel toOrderEfficiencyModel(OrderLogDocument orderLogDocument);

    List<OrderEfficiencyModel> toOrderEfficiencyModelList(List<OrderLogDocument> orderLogDocument);

    @Named("calculateTotalTime")
    default String calculateTotalTime(OrderLogDocument orderLogDocument) {
        LocalDateTime start = orderLogDocument.getCreatedAt();
        LocalDateTime end = orderLogDocument.getUpdatedAt();

        if (start == null || end == null) {
            return "0 seconds";
        }

        Duration duration = Duration.between(start, end);

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

    @Named("calculateTotalMinutes")
    default BigInteger calculateTotalMinutes(OrderLogDocument orderLogDocument) {
        LocalDateTime start = orderLogDocument.getCreatedAt();
        LocalDateTime end = orderLogDocument.getUpdatedAt();

        if (start == null || end == null) {
            return BigInteger.ZERO;
        }

        Duration duration = Duration.between(start, end);

        return BigInteger.valueOf(duration.toMinutes());
    }

}
