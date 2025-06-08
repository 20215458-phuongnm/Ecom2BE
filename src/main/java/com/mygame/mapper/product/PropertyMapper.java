package com.mygame.mapper.product;

import com.mygame.dto.product.PropertyRequest;
import com.mygame.dto.product.PropertyResponse;
import com.mygame.entity.product.Property;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper extends GenericMapper<Property, PropertyRequest, PropertyResponse> {
}
