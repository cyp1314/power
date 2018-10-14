package com.huowolf.controller;

import com.huowolf.model.Area;
import com.huowolf.model.Department;
import com.huowolf.model.Employee;
import com.huowolf.service.AreaService;
import com.huowolf.service.DepartmentService;
import com.huowolf.service.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huowolf on 2018/10/9.
 */
@Controller
public class IndexController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public String index(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("loginInfo");

        List<Area> areaList = ServiceUtil.loadAreaByEmployee(areaService,employee);

        if(areaList!=null){
            model.addAttribute("areaList",areaList);
        }

        List<Department> departmentList = departmentService.findAllDepartment();
        if(departmentList!=null){
            model.addAttribute("departmentList",departmentList);
        }

        return "index";
    }

    @GetMapping("/tologin")
    public String tologin(){
        return "login";
    }



}
