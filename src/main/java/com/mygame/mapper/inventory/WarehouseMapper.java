package com.mygame.mapper.inventory;

import com.mygame.dto.inventory.WarehouseRequest;
import com.mygame.dto.inventory.WarehouseResponse;
import com.mygame.entity.inventory.Warehouse;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface WarehouseMapper extends GenericMapper<Warehouse, WarehouseRequest, WarehouseResponse> {}
