package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.DestinationRequest;
import com.mygame.dto.inventory.DestinationResponse;
import com.mygame.entity.inventory.Destination;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface DestinationMapper extends GenericMapper<Destination, DestinationRequest, DestinationResponse> {}
