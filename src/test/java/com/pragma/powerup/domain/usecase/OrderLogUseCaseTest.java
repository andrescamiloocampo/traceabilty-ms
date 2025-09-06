package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.InvalidOwnerException;
import com.pragma.powerup.domain.model.OrderEfficiencyModel;
import com.pragma.powerup.domain.model.OrderLogModel;
import com.pragma.powerup.domain.model.OrderStatusModel;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantOwnerPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderLogUseCaseTest {
    private IOrderLogPersistencePort orderLogPersistencePort;
    private IRestaurantOwnerPersistencePort restaurantOwnerPersistencePort;
    private OrderLogUseCase useCase;

    @BeforeEach
    void setUp() {
        orderLogPersistencePort = mock(IOrderLogPersistencePort.class);
        restaurantOwnerPersistencePort = mock(IRestaurantOwnerPersistencePort.class);
        useCase = new OrderLogUseCase(orderLogPersistencePort, restaurantOwnerPersistencePort);
    }

    @Test
    void shouldThrowExceptionWhenOwnershipIsInvalid() {
        when(restaurantOwnerPersistencePort.getOwnership(1, 1)).thenReturn(false);

        assertThrows(InvalidOwnerException.class, () ->
                useCase.getOrderEfficiencyByEmployee(10L, 1L, 1L));
    }

    @Test
    void shouldReturnZeroWhenNoOrdersExist() {
        when(restaurantOwnerPersistencePort.getOwnership(1, 1)).thenReturn(true);
        when(orderLogPersistencePort.getOrdersByChefId(10L)).thenReturn(Collections.emptyList());

        OrderEfficiencyModel result = useCase.getOrderEfficiencyByEmployee(10L, 1L, 1L);

        assertEquals(BigInteger.ZERO, result.getTotalMinutes());
        assertEquals(10L, result.getChefId());
    }

    @Test
    void shouldIgnoreOrdersWithNullDates() {
        when(restaurantOwnerPersistencePort.getOwnership(1, 1)).thenReturn(true);

        OrderLogModel invalidOrder = new OrderLogModel();
        invalidOrder.setCreatedAt(null);
        invalidOrder.setUpdatedAt(null);

        when(orderLogPersistencePort.getOrdersByChefId(10L)).thenReturn(List.of(invalidOrder));

        OrderEfficiencyModel result = useCase.getOrderEfficiencyByEmployee(10L, 1L, 1L);

        assertEquals(BigInteger.ZERO, result.getTotalMinutes());
    }

    @Test
    void shouldCalculateAverageWithoutRoundingUp() {
        when(restaurantOwnerPersistencePort.getOwnership(1, 1)).thenReturn(true);

        LocalDateTime start = LocalDateTime.now().minusMinutes(10);
        LocalDateTime end = LocalDateTime.now();

        OrderLogModel order1 = new OrderLogModel();
        order1.setCreatedAt(start);
        order1.setUpdatedAt(end);

        OrderLogModel order2 = new OrderLogModel();
        order2.setCreatedAt(start);
        order2.setUpdatedAt(end.minusMinutes(1));

        when(orderLogPersistencePort.getOrdersByChefId(10L)).thenReturn(Arrays.asList(order1, order2));

        OrderEfficiencyModel result = useCase.getOrderEfficiencyByEmployee(10L, 1L, 1L);

        assertEquals(BigInteger.TEN, result.getTotalMinutes());
    }

    @Test
    void shouldCalculateAverageWithRoundingDown() {
        when(restaurantOwnerPersistencePort.getOwnership(1, 1)).thenReturn(true);

        LocalDateTime now = LocalDateTime.now();

        OrderLogModel order1 = new OrderLogModel();
        order1.setCreatedAt(now.minusMinutes(8));
        order1.setUpdatedAt(now);

        OrderLogModel order2 = new OrderLogModel();
        order2.setCreatedAt(now.minusMinutes(4));
        order2.setUpdatedAt(now);

        when(orderLogPersistencePort.getOrdersByChefId(10L)).thenReturn(Arrays.asList(order1, order2));

        OrderEfficiencyModel result = useCase.getOrderEfficiencyByEmployee(10L, 1L, 1L);

        assertEquals(BigInteger.valueOf(6), result.getTotalMinutes());
    }
}