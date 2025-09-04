package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.application.handler.IOrderLogHandler;
import com.pragma.powerup.application.mapper.IOrderLogRequestMapper;
import com.pragma.powerup.application.mapper.IOrderLogResponseMapper;
import com.pragma.powerup.domain.api.IOrderLogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderLogHandler implements IOrderLogHandler {
    private final IOrderLogServicePort orderLogServicePort;
    private final IOrderLogResponseMapper orderLogResponseMapper;
    private final IOrderLogRequestMapper orderLogRequestMapper;

    @Override
    public void logOrderStatusChange(OrderLogRequestDto dto) {
        orderLogServicePort.logOrderStatusChange(orderLogRequestMapper.toOrderLog(dto));
    }

    @Override
    public OrderLogResponseDto getOrderLogs(Long orderId) {
        return orderLogResponseMapper.toResponse(orderLogServicePort.getOrderLogs(orderId));
    }

    @Override
    public List<OrderLogResponseDto> getAllOrderLogs(Long customerId) {
        return orderLogResponseMapper.toResponseList(orderLogServicePort.getAllOrderLogs(customerId));
    }
}
