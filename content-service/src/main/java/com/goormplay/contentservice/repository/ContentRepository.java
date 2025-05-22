package com.goormplay.contentservice.repository;

import com.goormplay.contentservice.entity.Content;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends MongoRepository<Content, String>, ContentRepositoryCustom {
    // 단순한 조회는 명명 규칙으로 해결 가능한 것들만 유지
    List<Content> findByGenreContaining(String genre);
    List<Content> findByKind(String kind);

    // 단순 ID 조회
    Optional<Content> findById(String id);
}
