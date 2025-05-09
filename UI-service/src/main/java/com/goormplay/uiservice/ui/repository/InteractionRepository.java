package com.goormplay.uiservice.ui.repository;

import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.goormplay.uiservice.ui.entity.InteractionEntity;

@Repository
public interface InteractionRepository extends MongoRepository<InteractionEntity, String> {
    Optional<InteractionEntity> findByUserIdAndContentId(String userId, String contentId); 
}
