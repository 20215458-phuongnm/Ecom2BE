package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.DocketRequest;
import com.mygame.dto.inventory.DocketResponse;
import com.mygame.entity.inventory.Docket;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, DocketReasonMapper.class, WarehouseMapper.class, DocketVariantMapper.class})
public interface DocketMapper extends GenericMapper<Docket, DocketRequest, DocketResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachDocket")
    @Mapping(source = "reasonId", target = "reason")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "orderId", target = "order")
    Docket requestToEntity(DocketRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachDocket")
    @Mapping(source = "reasonId", target = "reason")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "orderId", target = "order")
    Docket partialUpdate(@MappingTarget Docket entity, DocketRequest request);

}
