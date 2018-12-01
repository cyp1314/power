package com.huowolf.service;

import com.huowolf.mapper.AreaMapper;
import com.huowolf.model.Area;
import com.huowolf.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huowolf on 2018/10/12.
 */
@Service
@CacheConfig(cacheNames = "content")
public class AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Cacheable(key = "'area'")
    public List<Area> findAllArea(){
        return areaMapper.selectAll();
    }

    /**
     * 根据区域id查询区域
     * @param areaId
     * @return
     */
    public Area findAreaById(Integer areaId){
        Area area = new Area();
        area.setId(areaId);
        return areaMapper.selectOne(area);
    }

    /**
     * 根据员工加载区域数据
     * @param employee
     * @return
     */
    public List<Area> loadAreaByEmployee(Employee employee){
        List<Area> areaList = null;
        if(employee.getName().equals("admin")){
            areaList = this.findAllArea();
        }else{
            Area area = this.findAreaById(employee.getAreaId());
            areaList = new ArrayList<>();
            areaList.add(area);
        }
        return  areaList;
    }
}
