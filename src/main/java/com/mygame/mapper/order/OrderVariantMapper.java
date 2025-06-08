package com.mygame.mapper.order;

import com.mygame.dto.order.OrderVariantRequest;
import com.mygame.dto.order.OrderVariantResponse;
import com.mygame.entity.order.OrderVariant;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface OrderVariantMapper extends GenericMapper<OrderVariant, OrderVariantRequest, OrderVariantResponse> {

    @Override
    @Mapping(source = "variantId", target = "variant")
    OrderVariant requestToEntity(OrderVariantRequest request);

    @Override
    @Mapping(source = "variantId", target = "variant")
    OrderVariant partialUpdate(@MappingTarget OrderVariant entity, OrderVariantRequest request);

}
