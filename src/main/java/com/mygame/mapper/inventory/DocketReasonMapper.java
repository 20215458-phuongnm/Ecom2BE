package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.DocketReasonRequest;
import com.mygame.dto.inventory.DocketReasonResponse;
import com.mygame.entity.inventory.DocketReason;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocketReasonMapper extends GenericMapper<DocketReason, DocketReasonRequest, DocketReasonResponse> {}
