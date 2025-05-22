package com.goormplay.contentservice.repository;

import com.goormplay.contentservice.dto.ContentCardDTO;
import com.goormplay.contentservice.dto.ContentDetailDTO;
import com.goormplay.contentservice.dto.VideoDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContentRepositoryCustom {
    // DTO 직접 반환 메서드

    List<VideoDTO> findLatestContentCards(int limit);
    List<VideoDTO> findContentCardsByIds(List<ObjectId> ids);
    Optional<VideoDTO> findContentDetailById(String id);
    List<VideoDTO> findAllLatestContentCards();
    Page<VideoDTO> findLatestContents(Pageable pageable);
//    List<ContentCardDTO> findByReleaseDateAfter(LocalDate releaseDate);


    List<VideoDTO> findAllWithBaseFields();
    List<VideoDTO> findAllAsTrending();
    List<VideoDTO> findAllAsLatest();

    List<VideoDTO> findByIdsAsRecommended(List<ObjectId> contentIds);
}
