package com.mygame.mapper.product;

import com.mygame.dto.product.BrandRequest;
import com.mygame.dto.product.BrandResponse;
import com.mygame.entity.product.Brand;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper extends GenericMapper<Brand, BrandRequest, BrandResponse> {}
