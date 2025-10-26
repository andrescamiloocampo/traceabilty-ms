package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyResponseDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.application.handler.IOrderLogHandler;
import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderLogRestController {

    private final IOrderLogHandler orderLogHandler;

    @Operation(summary = "Get all order logs for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order logs retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<OrderLogResponseDto>> getAllOrderLogs(Authentication authentication) {
        Long customerId = Long.valueOf(authentication.getPrincipal().toString());
        return ResponseEntity.ok(orderLogHandler.getAllOrderLogs(customerId));
    }

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

    @Operation(summary = "Get time per order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders efficiency retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/efficiency/{restaurantId}")
    public ResponseEntity<List<OrderEfficiencyResponseDto>> getOrdersEfficiency(Authentication authentication, @PathVariable("restaurantId") long restaurantId) {
        long ownerId = Long.parseLong(authentication.getPrincipal().toString());
        return ResponseEntity.ok(orderLogHandler.getOrderEfficiencyReport(ownerId, restaurantId));
    }

    @Operation(summary = "Get efficiency per employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee efficiency retrieved"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/performance/{restaurantId}/{employeeId}")
    public ResponseEntity<EmployeeEfficiencyResponseDto> getEmployeePerformance(Authentication authentication,
                                                                                @PathVariable("restaurantId") long restaurantId,
                                                                                @PathVariable long employeeId){
        long ownerId = Long.parseLong(authentication.getPrincipal().toString());
        return ResponseEntity.ok(orderLogHandler.getEmployeePerformance(restaurantId,ownerId,employeeId));
    }
}
