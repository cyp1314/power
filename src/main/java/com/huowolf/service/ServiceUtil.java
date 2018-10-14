package com.huowolf.service;

import com.huowolf.model.Area;
import com.huowolf.model.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huowolf on 2018/10/14.
 */
public class ServiceUtil {

    /**
     * 根据员工加载区域数据
     * @param employee
     * @return
     */
    public static List<Area> loadAreaByEmployee(AreaService areaService, Employee employee){
        List<Area> areaList = null;
        if(employee.getName().equals("admin")){
            areaList = areaService.findAllArea();
        }else{
            Area area = areaService.findAreaById(employee.getAreaId());
            areaList = new ArrayList<>();
            areaList.add(area);
        }
        return  areaList;
    }
}
