package com.goormplay.contentservice.content.repository;

import com.goormplay.contentservice.content.dto.VideoDTO;
import com.goormplay.contentservice.content.entity.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContentRepositoryCustomImpl implements ContentRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<VideoDTO> findLatestContents(Pageable pageable) {
        Criteria criteria = Criteria.where("releaseDate").exists(true);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(pageable.getSort()),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize()),
                getBaseProjection(),
                addLatestAttribute()
        );

        List<VideoDTO> results = mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();

        long total = mongoTemplate.count(Query.query(criteria), Content.class);

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<VideoDTO> findAllLatestContentCards() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "releaseDate")),
                getBaseProjection(),
                addLatestAttribute()
        );

        return mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();
    }

    @Override
    public List<VideoDTO> findLatestContentCards(int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "releaseDate")),
                Aggregation.limit(limit),
                getBaseProjection(),
                addLatestAttribute()
        );

        return mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();
    }

    @Override
    public List<VideoDTO> findContentCardsByIds(List<ObjectId> ids) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").in(ids)),
                getBaseProjection(),
                addRecommendedAttribute()
        );

        return mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();
    }

    @Override
    public Optional<VideoDTO> findContentDetailById(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Criteria criteria = Criteria.where("_id").is(objectId);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    getBaseProjection(),
                    addDefaultAttributes()
            );

            return Optional.ofNullable(mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                    .getUniqueMappedResult());
        }catch (IllegalArgumentException e){
            log.error("Invalid ObjectId format: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<VideoDTO> findAllWithBaseFields() {
        Aggregation aggregation = Aggregation.newAggregation(
                getBaseProjection(),
                addDefaultAttributes()
        );

        return mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();
    }

    @Override
    public List<VideoDTO> findAllAsTrending() {
        Aggregation aggregation = Aggregation.newAggregation(
                getBaseProjection(),
                addTrendingAttribute()
        );

        return mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();
    }

    @Override
    public List<VideoDTO> findAllAsLatest() {
        Aggregation aggregation = Aggregation.newAggregation(
                getBaseProjection(),
                addLatestAttribute()
        );

        return mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();
    }

    @Override
    public List<VideoDTO> findByIdsAsRecommended(List<ObjectId> contentIds) {
        Aggregation aggregation = Aggregation.newAggregation(
                // 1. ID 기반 필터링
                Aggregation.match(Criteria.where("_id").in(contentIds)),
                // 2. 기본 필드 프로젝션
                getBaseProjection(),
                // 3. recommended = true 설정
                addRecommendedAttribute()
        );

        return mongoTemplate.aggregate(aggregation, "contents", VideoDTO.class)
                .getMappedResults();
    }


    private ProjectionOperation getBaseProjection() {
        return Aggregation.project()
                .and("_id").as("id")
                .and("title").as("title")
                .and("kind").as("kind")
                .and("genre").as("genre")
                .and("videoId").as("videoId")
                .and("thumbnail").as("thumbnail")
                .and("synopsis").as("synopsis")
                .and("year").as("year")
                .and("KMRB").as("KMRB")
                .and("cast").as("cast")
                .and("provider").as("provider")
                .and("director").as("director")
                .and("releaseDate").as("releaseDate");
    }

    private AddFieldsOperation addDefaultAttributes() {
        return Aggregation.addFields()
                .addField("trending").withValue(false)
                .addField("latest").withValue(false)
                .addField("recommended").withValue(false)
                .build();
    }

    private AddFieldsOperation addTrendingAttribute() {
        return Aggregation.addFields()
                .addField("trending").withValue(true)
                .addField("latest").withValue(false)
                .addField("recommended").withValue(false)
                .build();
    }

    private AddFieldsOperation addLatestAttribute() {
        return Aggregation.addFields()
                .addField("trending").withValue(false)
                .addField("latest").withValue(true)
                .addField("recommended").withValue(false)
                .build();
    }

    private AddFieldsOperation addRecommendedAttribute() {
        return Aggregation.addFields()
                .addField("trending").withValue(false)
                .addField("latest").withValue(false)
                .addField("recommended").withValue(true)
                .build();
    }

    private ProjectionOperation getCardProjection() {
        return Aggregation.project()
                .and("_id").as("id")
                .and("title").as("title")
                .and("kind").as("kind")
                .and("genre").as("genre")
                .and("thumbnail").as("thumbnail")
                .and("videoId").as("videoId");
    }





    private ProjectionOperation getDetailProjection() {
        return Aggregation.project()
                .and("_id").as("id")
                .and("title").as("title")
                .and("kind").as("kind")
                .and("genre").as("genre")
                .and("year").as("year")
                .and("KMRB").as("KMRB")
                .and("cast").as("cast")
                .and("director").as("director")
                .and("videoId").as("videoId")
                .and("releaseDate").as("releaseDate")
                .and("thumbnail").as("thumbnail")
                .and("synopsis").as("synopsis")
                .and("provider").as("provider");
    }

}

