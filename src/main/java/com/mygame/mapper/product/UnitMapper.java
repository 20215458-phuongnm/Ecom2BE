package com.mygame.mapper.product;

import com.mygame.dto.product.UnitRequest;
import com.mygame.dto.product.UnitResponse;
import com.mygame.entity.product.Unit;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper extends GenericMapper<Unit, UnitRequest, UnitResponse> {
}
