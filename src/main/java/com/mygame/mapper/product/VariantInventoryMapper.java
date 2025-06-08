package com.mygame.mapper.product;

import com.mygame.dto.inventory.VariantInventoryResponse;
import com.mygame.mapper.inventory.DocketVariantMapper;
import com.mygame.inventory.VariantInventory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DocketVariantMapper.class)
public interface VariantInventoryMapper {

    VariantInventoryResponse toResponse(VariantInventory variantInventory);

    List<VariantInventoryResponse> toResponse(List<VariantInventory> variantInventory);

}
