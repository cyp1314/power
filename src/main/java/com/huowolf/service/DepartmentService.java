package com.huowolf.service;

import com.huowolf.mapper.DepartmentMapper;
import com.huowolf.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huowolf on 2018/10/12.
 */
@Service
@CacheConfig(cacheNames = "content")
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Cacheable(key = "'department'")
    public List<Department> findAllDepartment(){
        return  departmentMapper.selectAll();
    }

    public Department findDepartmentById(Integer id){
        return departmentMapper.selectByPrimaryKey(id);
    }
}
