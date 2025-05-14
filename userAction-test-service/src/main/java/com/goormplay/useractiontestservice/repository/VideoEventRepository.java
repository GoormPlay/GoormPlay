package com.goormplay.useractiontestservice.repository;

import com.goormplay.useractiontestservice.domain.VideoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoEventRepository extends MongoRepository<VideoEvent, String>{

}
