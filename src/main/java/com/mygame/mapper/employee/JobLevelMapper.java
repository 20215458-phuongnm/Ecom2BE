package com.mygame.mapper.employee;

import com.mygame.dto.employee.JobLevelRequest;
import com.mygame.dto.employee.JobLevelResponse;
import com.mygame.entity.employee.JobLevel;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobLevelMapper extends GenericMapper<JobLevel, JobLevelRequest, JobLevelResponse> {
}
