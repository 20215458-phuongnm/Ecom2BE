package com.mygame.mapper.address;

import com.mygame.dto.address.WardRequest;
import com.mygame.dto.address.WardResponse;
import com.mygame.entity.address.Ward;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface WardMapper extends GenericMapper<Ward, WardRequest, WardResponse> {

    @Override
    @Mapping(source = "districtId", target = "district")
    Ward requestToEntity(WardRequest request);

    @Override
    @Mapping(source = "districtId", target = "district")
    Ward partialUpdate(@MappingTarget Ward entity, WardRequest request);

}
