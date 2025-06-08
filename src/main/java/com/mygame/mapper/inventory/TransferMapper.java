package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.TransferRequest;
import com.mygame.dto.inventory.TransferResponse;
import com.mygame.entity.inventory.Transfer;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DocketMapper.class)
public interface TransferMapper extends GenericMapper<Transfer, TransferRequest, TransferResponse> {}
