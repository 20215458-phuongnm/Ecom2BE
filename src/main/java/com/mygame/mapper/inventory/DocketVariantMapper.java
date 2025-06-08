package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.DocketVariantEliminatedResponse;
import com.mygame.dto.inventory.DocketVariantExtendedResponse;
import com.mygame.dto.inventory.DocketVariantRequest;
import com.mygame.dto.inventory.DocketVariantResponse;
import com.mygame.entity.inventory.DocketVariant;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DocketReasonMapper.class, WarehouseMapper.class, MapperUtils.class})
public interface DocketVariantMapper extends GenericMapper<DocketVariant, DocketVariantRequest, DocketVariantResponse> {

    @Override
    @Mapping(source = "variantId", target = "variant")
    DocketVariant requestToEntity(DocketVariantRequest request);

    @Override
    @Mapping(source = "variantId", target = "variant")
    DocketVariant partialUpdate(@MappingTarget DocketVariant entity, DocketVariantRequest request);

    DocketVariantExtendedResponse docketVariantToDocketVariantExtendedResponse(DocketVariant docketVariant);

    DocketVariantEliminatedResponse docketVariantToDocketVariantEliminatedResponse(DocketVariant docketVariant);

}
