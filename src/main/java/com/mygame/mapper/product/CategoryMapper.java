package com.mygame.mapper.product;

import com.mygame.dto.product.CategoryRequest;
import com.mygame.dto.product.CategoryResponse;
import com.mygame.entity.product.Category;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface CategoryMapper extends GenericMapper<Category, CategoryRequest, CategoryResponse> {

    @Override
    @Mapping(source = "parentCategoryId", target = "parentCategory")
    Category requestToEntity(CategoryRequest request);

    @Override
    @Mapping(source = "parentCategoryId", target = "parentCategory")
    Category partialUpdate(@MappingTarget Category entity, CategoryRequest request);

}
