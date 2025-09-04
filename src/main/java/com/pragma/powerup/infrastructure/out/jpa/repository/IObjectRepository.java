package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.ObjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IObjectRepository extends MongoRepository<ObjectEntity, String> {

}