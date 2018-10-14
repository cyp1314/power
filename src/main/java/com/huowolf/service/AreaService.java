package com.huowolf.service;

import com.huowolf.mapper.AreaMapper;
import com.huowolf.model.Area;
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
public class AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Cacheable(key = "'area'")
    public List<Area> findAllArea(){
        return areaMapper.selectAll();
    }

    public Area findAreaById(Integer areaId){
        Area area = new Area();
        area.setId(areaId);
        return areaMapper.selectOne(area);
    }
}
