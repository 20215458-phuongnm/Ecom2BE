package com.mygame.mapper.address;

import com.mygame.dto.address.DistrictRequest;
import com.mygame.dto.address.DistrictResponse;
import com.mygame.entity.address.District;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface DistrictMapper extends GenericMapper<District, DistrictRequest, DistrictResponse> {

    @Override
    @Mapping(source = "provinceId", target = "province")
    District requestToEntity(DistrictRequest request);

    @Override
    @Mapping(source = "provinceId", target = "province")
    District partialUpdate(@MappingTarget District entity, DistrictRequest request);

}
