package com.mygame.mapper.customer;

import com.mygame.dto.customer.CustomerResourceRequest;
import com.mygame.dto.customer.CustomerResourceResponse;
import com.mygame.entity.customer.CustomerResource;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerResourceMapper extends GenericMapper<CustomerResource, CustomerResourceRequest, CustomerResourceResponse> {
}
