package com.goormplay.contentservice.repository;

import com.goormplay.contentservice.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContentRepository extends MongoRepository<Content, String> {
    List<Content> findByGenreContaining(String genre);
    List<Content> findByContentType(String contentType);
    List<Content> findByReleaseDateAfterOrderByReleaseDateDesc(LocalDate releaseDate);

    @Query("{ 'releaseDate': { $gte: ?0, $lte: ?1 } }")
    List<Content> findByReleaseDateBetween(LocalDate start, LocalDate end);

    @Query("{ 'releaseDate': { $exists: true }, 'contentType': ?0 }")
    Page<Content> findLatestContentsByContentType(String contentType, Pageable pageable);

    @Query("{ 'releaseDate': { $exists: true }}")
    Page<Content> findLatestContents(Pageable pageable);

    @Query("{ 'releaseDate': { $exists: true }}")
    List<Content> findAllLatestContents();  // Pageable 파라미터 없이
}
