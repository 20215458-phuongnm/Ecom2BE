package com.mygame.mapper.employee;

import com.mygame.dto.employee.EmployeeRequest;
import com.mygame.dto.employee.EmployeeResponse;
import com.mygame.entity.employee.Employee;
import com.mygame.mapper.GenericMapper;
import com.mygame.mapper.authentication.UserMapper;
import com.mygame.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtils.class, UserMapper.class})
public interface EmployeeMapper extends GenericMapper<Employee, EmployeeRequest, EmployeeResponse> {

    @Override
    @Mapping(source = "officeId", target = "office")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "jobTypeId", target = "jobType")
    @Mapping(source = "jobLevelId", target = "jobLevel")
    @Mapping(source = "jobTitleId", target = "jobTitle")
    Employee requestToEntity(EmployeeRequest request);

    @Override
    @Mapping(source = "officeId", target = "office")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "jobTypeId", target = "jobType")
    @Mapping(source = "jobLevelId", target = "jobLevel")
    @Mapping(source = "jobTitleId", target = "jobTitle")
    Employee partialUpdate(@MappingTarget Employee entity, EmployeeRequest request);

}
