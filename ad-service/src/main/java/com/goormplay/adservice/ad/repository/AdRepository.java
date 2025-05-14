package com.goormplay.adservice.ad.repository;

import com.goormplay.adservice.ad.entity.Advertisement;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AdRepository extends MongoRepository<Advertisement, String> {
    List<Advertisement> findByType(String type);
}