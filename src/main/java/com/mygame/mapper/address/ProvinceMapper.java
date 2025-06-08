package com.mygame.mapper.address;

import com.mygame.dto.address.ProvinceRequest;
import com.mygame.dto.address.ProvinceResponse;
import com.mygame.entity.address.Province;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper extends GenericMapper<Province, ProvinceRequest, ProvinceResponse> {
}
