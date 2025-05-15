package com.goormplay.adadminservice.tool;

import com.goormplay.adadminservice.dto.AdvertiserAccountDTO;
import com.goormplay.adadminservice.entity.AdvertiserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdvertiserAccountMapper {
    AdvertiserAccountDTO toDto(AdvertiserAccount entity);
    AdvertiserAccount toEntity(AdvertiserAccountDTO dto);
}
