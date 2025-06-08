package com.mygame.mapper.authentication;

import com.mygame.dto.authentication.RoleRequest;
import com.mygame.dto.authentication.RoleResponse;
import com.mygame.entity.authentication.Role;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends GenericMapper<Role, RoleRequest, RoleResponse> {
}
