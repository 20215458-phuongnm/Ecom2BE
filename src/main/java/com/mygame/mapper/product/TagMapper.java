package com.mygame.mapper.product;

import com.mygame.dto.product.TagRequest;
import com.mygame.dto.product.TagResponse;
import com.mygame.entity.product.Tag;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper extends GenericMapper<Tag, TagRequest, TagResponse> {
}
