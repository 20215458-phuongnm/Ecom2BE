package com.mygame.mapper.product;

import com.mygame.dto.product.SpecificationRequest;
import com.mygame.dto.product.SpecificationResponse;
import com.mygame.entity.product.Specification;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecificationMapper extends GenericMapper<Specification, SpecificationRequest, SpecificationResponse> {
}
