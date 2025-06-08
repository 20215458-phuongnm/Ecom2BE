package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.PurchaseOrderVariantRequest;
import com.mygame.dto.inventory.PurchaseOrderVariantResponse;
import com.mygame.entity.inventory.PurchaseOrderVariant;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface PurchaseOrderVariantMapper extends GenericMapper<PurchaseOrderVariant, PurchaseOrderVariantRequest,
        PurchaseOrderVariantResponse> {

    @Override
    @Mapping(source = "variantId", target = "variant")
    PurchaseOrderVariant requestToEntity(PurchaseOrderVariantRequest request);

    @Override
    @Mapping(source = "variantId", target = "variant")
    PurchaseOrderVariant partialUpdate(@MappingTarget PurchaseOrderVariant entity, PurchaseOrderVariantRequest request);

}
