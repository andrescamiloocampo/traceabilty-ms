package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.IRestaurantOwnerPersistencePort;
import com.pragma.powerup.infrastructure.out.feign.client.PlazaFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantOwnerAdapter implements IRestaurantOwnerPersistencePort {

    private final PlazaFeignClient plazaFeignClient;

    @Override
    public boolean getOwnership(int restaurantId, int ownerId) {
        return plazaFeignClient.getOwnership(restaurantId,ownerId);
    }
}