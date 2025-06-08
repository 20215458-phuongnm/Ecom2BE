package com.mygame.mapper.employee;

import com.mygame.dto.employee.JobTypeRequest;
import com.mygame.dto.employee.JobTypeResponse;
import com.mygame.entity.employee.JobType;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobTypeMapper extends GenericMapper<JobType, JobTypeRequest, JobTypeResponse> {
}
