package com.mygame.mapper.waybill;

import com.mygame.dto.waybill.WaybillRequest;
import com.mygame.dto.waybill.WaybillResponse;
import com.mygame.entity.waybill.Waybill;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.order.OrderMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtils.class, OrderMapper.class})
public interface WaybillMapper extends GenericMapper<Waybill, WaybillRequest, WaybillResponse> {

    @Override
    @Mapping(source = "orderId", target = "order")
    Waybill requestToEntity(WaybillRequest request);

    @Override
    @Mapping(source = "orderId", target = "order")
    Waybill partialUpdate(@MappingTarget Waybill entity, WaybillRequest request);

}
