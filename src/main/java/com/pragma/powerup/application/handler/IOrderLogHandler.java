package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;

import java.util.List;


public interface IOrderLogHandler {
    void logOrderStatusChange(OrderLogRequestDto orderLogRequestDto);
    OrderLogResponseDto getOrderLogs(Long orderId);
    List<OrderLogResponseDto> getAllOrderLogs(Long customerId);
    List<OrderEfficiencyResponseDto> getOrderEfficiencyReport(long ownerId,long restaurantId);
    EmployeeEfficiencyResponseDto getEmployeePerformance(long restaurantId, long ownerId, long employeeId);
}
