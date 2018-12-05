package com.huowolf.mapper;

import com.huowolf.dto.EmployeeTable;
import com.huowolf.model.Employee;
import com.huowolf.util.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("SELECT employee.id,employee.name,area.name AS area FROM employee,area WHERE TYPE=1 AND employee.`area_id`=area.`id`")
    List<EmployeeTable> findAllEmploee();
}