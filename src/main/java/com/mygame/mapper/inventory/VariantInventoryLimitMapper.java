package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.VariantInventoryLimitRequest;
import com.mygame.dto.inventory.VariantInventoryLimitResponse;
import com.mygame.entity.inventory.VariantInventoryLimit;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface VariantInventoryLimitMapper extends GenericMapper<VariantInventoryLimit, VariantInventoryLimitRequest,
        VariantInventoryLimitResponse> {

    @Override
    @Mapping(source = "variantId", target = "variant")
    VariantInventoryLimit requestToEntity(VariantInventoryLimitRequest request);

    @Override
    @Mapping(source = "variantId", target = "variant")
    VariantInventoryLimit partialUpdate(@MappingTarget VariantInventoryLimit entity, VariantInventoryLimitRequest request);

}
