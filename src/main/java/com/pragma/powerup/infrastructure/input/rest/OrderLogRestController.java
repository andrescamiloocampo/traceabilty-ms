package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.application.handler.IOrderLogHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/logs/orders")
@RequiredArgsConstructor
public class OrderLogRestController {

    private final IOrderLogHandler orderLogHandler;

    @Operation(summary = "Log order status change")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status change logged successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    public ResponseEntity<Void> logOrderStatusChange(@RequestBody OrderLogRequestDto orderLogRequestDto) {
        orderLogHandler.logOrderStatusChange(orderLogRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get order logs by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order logs retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderLogResponseDto> getOrderLogs(@PathVariable Long id) {
        return ResponseEntity.ok(orderLogHandler.getOrderLogs(id));
    }
}
