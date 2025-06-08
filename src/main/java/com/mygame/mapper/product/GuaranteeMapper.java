package com.mygame.mapper.product;

import com.mygame.dto.product.GuaranteeRequest;
import com.mygame.dto.product.GuaranteeResponse;
import com.mygame.entity.product.Guarantee;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuaranteeMapper extends GenericMapper<Guarantee, GuaranteeRequest, GuaranteeResponse> {
}
