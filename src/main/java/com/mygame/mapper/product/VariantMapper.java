package com.mygame.mapper.product;

import com.mygame.dto.product.VariantRequest;
import com.mygame.dto.product.VariantResponse;
import com.mygame.entity.product.Variant;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VariantMapper extends GenericMapper<Variant, VariantRequest, VariantResponse> {
}
