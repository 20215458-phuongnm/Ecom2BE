package com.mygame.mapper.customer;

import com.mygame.dto.customer.CustomerStatusRequest;
import com.mygame.dto.customer.CustomerStatusResponse;
import com.mygame.entity.customer.CustomerStatus;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerStatusMapper extends GenericMapper<CustomerStatus, CustomerStatusRequest, CustomerStatusResponse> {
}
