package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;


public interface IOrderLogHandler {
    void logOrderStatusChange(OrderLogRequestDto orderLogRequestDto);
    OrderLogResponseDto getOrderLogs(Long orderId);
}
