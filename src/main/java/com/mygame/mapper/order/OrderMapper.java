package com.mygame.mapper.order;


import com.mygame.dto.order.OrderRequest;
import com.mygame.dto.order.OrderResponse;
import com.mygame.entity.order.Order;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.authentication.UserMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, OrderResourceMapper.class, OrderCancellationReasonMapper.class, UserMapper.class,
                OrderVariantMapper.class})
public interface OrderMapper extends GenericMapper<Order, OrderRequest, OrderResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachOrder")
    @Mapping(source = "orderResourceId", target = "orderResource")
    @Mapping(source = "orderCancellationReasonId", target = "orderCancellationReason")
    @Mapping(source = "userId", target = "user")
    Order requestToEntity(OrderRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachOrder")
    @Mapping(source = "orderResourceId", target = "orderResource")
    @Mapping(source = "orderCancellationReasonId", target = "orderCancellationReason")
    @Mapping(source = "userId", target = "user")
    Order partialUpdate(@MappingTarget Order entity, OrderRequest request);

}
