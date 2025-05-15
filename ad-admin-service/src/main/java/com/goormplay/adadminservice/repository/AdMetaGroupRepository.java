package com.goormplay.adadminservice.repository;

import com.goormplay.adadminservice.entity.AdMetaGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdMetaGroupRepository extends MongoRepository<AdMetaGroup, String> {
    Optional<AdMetaGroup> findByAdInstanceId(String adInstanceId);
    Optional<AdMetaGroup> findByAdSnId(String adSnId);
    Optional<List<AdMetaGroup>> findByAdvertiserId(String advertiserId);
}
