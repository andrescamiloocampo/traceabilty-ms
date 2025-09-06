package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.application.handler.IOrderLogHandler;
import com.pragma.powerup.application.mapper.IOrderEfficiencyMapper;
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
    private final IOrderEfficiencyMapper orderEfficiencyMapper;

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

    @Override
    public List<OrderEfficiencyResponseDto> getOrderEfficiencyReport(long ownerId, long restaurantId) {
        return orderEfficiencyMapper.toResponseList(orderLogServicePort.getOrderEfficiencyReport(ownerId, restaurantId));
    }

    @Override
    public EmployeeEfficiencyResponseDto getEmployeePerformance(long restaurantId, long ownerId, long employeeId) {
        return orderLogResponseMapper.toEmployeeEfficiencyResponseDto(orderLogServicePort.getOrderEfficiencyByEmployee(employeeId,restaurantId,ownerId));
    }
}
