package com.mygame.mapper.order;


import com.mygame.dto.order.OrderResourceRequest;
import com.mygame.dto.order.OrderResourceResponse;
import com.mygame.entity.order.OrderResource;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.customer.CustomerResourceMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, CustomerResourceMapper.class})
public interface OrderResourceMapper extends GenericMapper<OrderResource, OrderResourceRequest, OrderResourceResponse> {

    @Override
    @Mapping(source = "customerResourceId", target = "customerResource")
    OrderResource requestToEntity(OrderResourceRequest request);

    @Override
    @Mapping(source = "customerResourceId", target = "customerResource")
    OrderResource partialUpdate(@MappingTarget OrderResource entity, OrderResourceRequest request);

}
