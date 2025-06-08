package com.mygame.mapper.general;

import com.mygame.dto.general.ImageRequest;
import com.mygame.dto.general.ImageResponse;
import com.mygame.entity.general.Image;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper extends GenericMapper<Image, ImageRequest, ImageResponse> {}
