package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderLogRequestDto;
import com.pragma.powerup.application.dto.response.OrderLogResponseDto;
import com.pragma.powerup.application.handler.IOrderLogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs/orders")
@RequiredArgsConstructor
public class OrderLogRestController {

    private final IOrderLogHandler orderLogHandler;

    @PostMapping
    public ResponseEntity<Void> logOrderStatusChange(@RequestBody OrderLogRequestDto orderLogRequestDto) {
        orderLogHandler.logOrderStatusChange(orderLogRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderLogResponseDto>> getOrderLogs(@PathVariable Long id) {
        return ResponseEntity.ok(orderLogHandler.getOrderLogs(id));
    }
}
