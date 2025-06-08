package com.mygame.mapper.employee;

import com.mygame.dto.employee.JobTitleRequest;
import com.mygame.dto.employee.JobTitleResponse;
import com.mygame.entity.employee.JobTitle;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobTitleMapper extends GenericMapper<JobTitle, JobTitleRequest, JobTitleResponse> {
}
