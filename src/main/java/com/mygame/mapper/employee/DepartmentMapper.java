package com.mygame.mapper.employee;

import com.mygame.dto.employee.DepartmentRequest;
import com.mygame.dto.employee.DepartmentResponse;
import com.mygame.entity.employee.Department;
import com.mygame.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper extends GenericMapper<Department, DepartmentRequest, DepartmentResponse> {
}
