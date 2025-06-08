package com.mygame.mapper.employee;

import com.mygame.dto.employee.OfficeRequest;
import com.mygame.dto.employee.OfficeResponse;
import com.mygame.entity.employee.Office;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface OfficeMapper extends GenericMapper<Office, OfficeRequest, OfficeResponse> {
}
