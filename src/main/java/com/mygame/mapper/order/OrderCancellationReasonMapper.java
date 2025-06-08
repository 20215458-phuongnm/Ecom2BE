package com.mygame.mapper.order;

import com.mygame.dto.order.OrderCancellationReasonRequest;
import com.mygame.dto.order.OrderCancellationReasonResponse;
import com.mygame.entity.order.OrderCancellationReason;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderCancellationReasonMapper extends GenericMapper<OrderCancellationReason, OrderCancellationReasonRequest,
        OrderCancellationReasonResponse> {}
