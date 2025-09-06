package com.pragma.powerup.domain.spi;

public interface IRestaurantOwnerPersistencePort {
    boolean getOwnership(int restaurantId, int ownerId);
}
