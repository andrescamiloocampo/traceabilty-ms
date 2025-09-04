package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderLogUseCaseTest {
    @Mock
    private IOrderLogPersistencePort orderLogPersistencePort;

    @InjectMocks
    private OrderLogUseCase orderLogUseCase;

    private OrderLogModel orderLogModel;

    @BeforeEach
    void setUp() {
        openMocks(this);

        orderLogModel = new OrderLogModel(
                1L,
                101L,
                202L,
                List.of(new OrderStatusModel("PENDING", "PREPARATION", LocalDateTime.now()))
        );
    }

    @Test
    void logOrderStatusChange_shouldCallPersistencePort() {
        OrderLogModel logModel = orderLogModel;

        orderLogUseCase.logOrderStatusChange(logModel);

        ArgumentCaptor<OrderStatusModel> statusCaptor = ArgumentCaptor.forClass(OrderStatusModel.class);

        verify(orderLogPersistencePort, times(1)).logOrderStatusChange(
                eq(logModel.getOrderId()),
                eq(logModel.getChefId()),
                eq(logModel.getCustomerId()),
                statusCaptor.capture()
        );

        OrderStatusModel capturedStatus = statusCaptor.getValue();
        assertEquals("PENDING", capturedStatus.getPreviousState());
        assertEquals("PREPARATION", capturedStatus.getNewState());
        assertNotNull(capturedStatus.getChangedAt());
    }

    @Test
    void getOrderLogs_shouldReturnOrderLog() {
        Long orderId = 1L;
        when(orderLogPersistencePort.getOrderLogByOrderId(orderId)).thenReturn(orderLogModel);

        OrderLogModel result = orderLogUseCase.getOrderLogs(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        verify(orderLogPersistencePort).getOrderLogByOrderId(orderId);
    }

    @Test
    void getAllOrderLogs_shouldReturnAllOrderLogsForCustomer() {
        Long customerId = 202L;
        List<OrderLogModel> mockLogs = List.of(orderLogModel,
                new OrderLogModel(2L, 101L, 202L, List.of(new OrderStatusModel("PREPARATION", "DONE", LocalDateTime.now())))
        );

        when(orderLogPersistencePort.getOrderLogsByCustomerId(customerId)).thenReturn(mockLogs);

        List<OrderLogModel> result = orderLogUseCase.getAllOrderLogs(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(customerId, result.get(0).getCustomerId());
        verify(orderLogPersistencePort).getOrderLogsByCustomerId(customerId);
    }
}