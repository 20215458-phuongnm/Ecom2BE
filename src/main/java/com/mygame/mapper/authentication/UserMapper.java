package com.mygame.mapper.authentication;

import com.mygame.dto.authentication.UserRequest;
import com.mygame.dto.authentication.UserResponse;
import com.mygame.dto.client.ClientEmailSettingUserRequest;
import com.mygame.dto.client.ClientPersonalSettingUserRequest;
import com.mygame.dto.client.ClientPhoneSettingUserRequest;
import com.mygame.entity.authentication.User;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.address.AddressMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtils.class, AddressMapper.class})
public interface UserMapper extends GenericMapper<User, UserRequest, UserResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachUser")
    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword")
    User requestToEntity(UserRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachUser")
    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, ClientPersonalSettingUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, ClientPhoneSettingUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, ClientEmailSettingUserRequest request);

}
