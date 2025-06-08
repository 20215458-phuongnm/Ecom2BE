package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.CountVariantRequest;
import com.mygame.dto.inventory.CountVariantResponse;
import com.mygame.entity.inventory.CountVariant;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface CountVariantMapper extends GenericMapper<CountVariant, CountVariantRequest, CountVariantResponse> {

    @Override
    @Mapping(source = "variantId", target = "variant")
    CountVariant requestToEntity(CountVariantRequest request);

    @Override
    @Mapping(source = "variantId", target = "variant")
    CountVariant partialUpdate(@MappingTarget CountVariant entity, CountVariantRequest request);

}
