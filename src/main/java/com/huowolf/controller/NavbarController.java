package com.huowolf.controller;

import com.huowolf.dto.NavbarNode;
import com.huowolf.model.Area;
import com.huowolf.model.Department;
import com.huowolf.model.Employee;
import com.huowolf.service.AreaService;
import com.huowolf.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huowolf on 2018/12/1.
 */
@RestController
public class NavbarController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 构建左侧菜单树
     * @param session
     * @return
     */
    @RequestMapping("/navbar")
    public List<NavbarNode> setNavbar(HttpSession session){
        Employee employee = (Employee) session.getAttribute("loginInfo");

        List<Area> areaList = areaService.loadAreaByEmployee(employee);
        List<Department> departmentList = departmentService.findAllDepartment();

        List<NavbarNode> navbarNodes = new ArrayList<>();
        for(Area area:areaList){
            NavbarNode navbarNode = new NavbarNode();
            navbarNode.setTitle(area.getName());

            List<NavbarNode> children = new ArrayList<>();
            for(Department department : departmentList){
                NavbarNode childNode = new NavbarNode();
                childNode.setTitle(department.getName());
                childNode.setHref("/user/list?area_id="+area.getId()+"&department_id="+department.getId());
                children.add(childNode);
            }

            navbarNode.setChildren(children);
            navbarNodes.add(navbarNode);
        }

        return navbarNodes;
    }

}
