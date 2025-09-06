package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IObjectServicePort;
import com.pragma.powerup.domain.api.IOrderLogServicePort;
import com.pragma.powerup.domain.spi.IObjectPersistencePort;
import com.pragma.powerup.domain.spi.IOrderLogPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantOwnerPersistencePort;
import com.pragma.powerup.domain.usecase.ObjectUseCase;
import com.pragma.powerup.domain.usecase.OrderLogUseCase;
import com.pragma.powerup.infrastructure.out.feign.adapter.RestaurantOwnerAdapter;
import com.pragma.powerup.infrastructure.out.feign.client.PlazaFeignClient;
import com.pragma.powerup.infrastructure.out.jpa.adapter.ObjectJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IObjectRepository;
import com.pragma.powerup.infrastructure.out.mongo.adapter.OrderLogMongoAdapter;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderLogDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IOrderStatusDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IOrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;
    private final IOrderLogRepository orderLogRepository;
    private final IOrderLogDocumentMapper orderLogDocumentMapper;
    private final IOrderStatusDocumentMapper orderStatusDocumentMapper;
    private final PlazaFeignClient plazaFeignClient;

    @Bean
    public IObjectPersistencePort objectPersistencePort() {
        return new ObjectJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IObjectServicePort objectServicePort() {
        return new ObjectUseCase(objectPersistencePort());
    }

    @Bean
    public IOrderLogPersistencePort orderLogPersistencePort() {
        return new OrderLogMongoAdapter(orderLogRepository,orderLogDocumentMapper,orderStatusDocumentMapper);
    }

    @Bean
    public IRestaurantOwnerPersistencePort restaurantOwnerPersistencePort(){
        return new RestaurantOwnerAdapter(plazaFeignClient);
    }

    @Bean
    public IOrderLogServicePort orderLogServicePort() {
        return new OrderLogUseCase(orderLogPersistencePort(),restaurantOwnerPersistencePort());
    }
}