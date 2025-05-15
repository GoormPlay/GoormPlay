package com.goormplay.adadminservice.tool;

import com.goormplay.adadminservice.dto.AdMetaGroupDTO;
import com.goormplay.adadminservice.entity.AdMetaGroup;
import com.goormplay.adadminservice.entity.AdStatus;
import com.goormplay.adadminservice.response.AdResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdMetaGroupMapper {
    @Mapping(target = "status", source = "status.statusCode")
    @Mapping(target = "ads", source = "ads")
    AdMetaGroupDTO toDto(AdMetaGroup entity);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "id", ignore = true) // id는 자동 생성되므로 무시
    @Mapping(target = "ads", source = "ads")
    AdMetaGroup toEntity(AdMetaGroupDTO dto);

    @Mapping(target = "type", source = "type")
    @Mapping(target = "thumbnail", source = "thumbnail")
    @Mapping(target = "embedUrl", source = "embedUrl")
    @Mapping(target = "impression", source = "impressions")
    @Mapping(target = "clicks", source = "clicks")
    AdMetaGroupDTO.AdVariantDTO toVariantDto(AdMetaGroup.AdVariant variant);

    @Mapping(target = "type", source = "type")
    @Mapping(target = "thumbnail", source = "thumbnail")
    @Mapping(target = "embedUrl", source = "embedUrl")
    @Mapping(target = "impressions", source = "impression")
    @Mapping(target = "clicks", source = "clicks")
    AdMetaGroup.AdVariant toVariant(AdMetaGroupDTO.AdVariantDTO variantDto);

    List<AdMetaGroupDTO> toDtoList(List<AdMetaGroup> entityList);
    List<AdMetaGroup> toEntityList(List<AdMetaGroupDTO> dtoList);

    @Mapping(target = "id", source = "adMetaGroup.id")
    @Mapping(target = "adSnId", source = "adMetaGroup.adSnId")
    @Mapping(target = "type", source = "variant.type")
    @Mapping(target = "title", source = "adMetaGroup.title")
    @Mapping(target = "thumbnailUrl", source = "variant.thumbnail")
    @Mapping(target = "link", source = "variant.embedUrl")
    @Mapping(target = "impressions", source = "variant.impressions")
    @Mapping(target = "clicks", source = "variant.clicks")
    @Mapping(target = "balance", source = "adMetaGroup.budget")
    @Mapping(target = "cpc", constant = "100") // 클릭당 비용 고정값 설정
    AdResponseDTO toAdResponse(AdMetaGroup adMetaGroup, AdMetaGroup.AdVariant variant);

    default List<AdMetaGroupDTO.AdVariantDTO> mapVariants(List<AdMetaGroup.AdVariant> variants) {
        if (variants == null) {
            return null;
        }
        return variants.stream()
                .map(this::toVariantDto)
                .collect(Collectors.toList());
    }

    default List<AdMetaGroup.AdVariant> mapVariantDtos(List<AdMetaGroupDTO.AdVariantDTO> variantDtos) {
        if (variantDtos == null) {
            return null;
        }
        return variantDtos.stream()
                .map(this::toVariant)
                .collect(Collectors.toList());
    }
}
