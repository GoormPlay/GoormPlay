package com.goormplay.contentservice.content.repository;

import com.goormplay.contentservice.content.entity.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

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
