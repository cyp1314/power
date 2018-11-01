package com.huowolf.config;

import com.huowolf.mapper.AreaMapper;
import com.huowolf.mapper.DepartmentMapper;
import com.huowolf.model.Area;
import com.huowolf.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

/**
 * 获取系统公共数据bean
 * Created by huowolf on 2018/10/27.
 */
@Configuration
public class SysDataConfig {

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取区域数据bean
     * @return
     */
    @Bean(name = "areaMap")
    public HashMap<String,Integer> getAreaMap(){
        List<Area> areaList = areaMapper.selectAll();

        HashMap areaMap = new HashMap();
        for (Area area:areaList) {
            areaMap.put(area.getName(),area.getId());
        }

        return areaMap;
    }

    /**
     * 获取部门数据bean
     * @return
     */
    @Bean(name = "departmentMap")
    public HashMap<String,Integer> getDepartmentMap(){
        List<Department> departmentList = departmentMapper.selectAll();

        HashMap departmentMap = new HashMap();
        for (Department department:departmentList) {
            departmentMap.put(department.getName(),department.getId());
        }

        return departmentMap;
    }
}
