package com.mygame.mapper.general;

import com.mygame.dto.general.NotificationRequest;
import com.mygame.dto.general.NotificationResponse;
import com.mygame.entity.general.Notification;
import com.mygame.mapper.GenericMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface NotificationMapper extends GenericMapper<Notification, NotificationRequest, NotificationResponse> {

    @Override
    @Mapping(source = "userId", target = "user")
    Notification requestToEntity(NotificationRequest request);

    @Override
    @Mapping(source = "userId", target = "user")
    Notification partialUpdate(@MappingTarget Notification entity, NotificationRequest request);

}
