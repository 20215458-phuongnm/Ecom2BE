package com.mygame.mapper.customer;

import com.mygame.dto.customer.CustomerGroupRequest;
import com.mygame.dto.customer.CustomerGroupResponse;
import com.mygame.entity.customer.CustomerGroup;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerGroupMapper extends GenericMapper<CustomerGroup, CustomerGroupRequest, CustomerGroupResponse> {
}
