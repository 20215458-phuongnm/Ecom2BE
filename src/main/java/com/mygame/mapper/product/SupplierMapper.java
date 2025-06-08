package com.mygame.mapper.product;

import com.mygame.dto.product.SupplierRequest;
import com.mygame.dto.product.SupplierResponse;
import com.mygame.entity.product.Supplier;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface SupplierMapper extends GenericMapper<Supplier, SupplierRequest, SupplierResponse> {
}
