package com.huowolf.controller;

import com.huowolf.dto.TableResponse;
import com.huowolf.dto.UserTable;
import com.huowolf.model.Area;
import com.huowolf.model.Department;
import com.huowolf.model.Employee;
import com.huowolf.service.AreaService;
import com.huowolf.service.DepartmentService;
import com.huowolf.service.ServiceUtil;
import com.huowolf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huowolf on 2018/10/12.
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("loginInfo");

        List<Area> areaList = ServiceUtil.loadAreaByEmployee(areaService,employee);

        if(areaList!=null){
            model.addAttribute("areaList",areaList);
        }

        List<Department> departmentList = departmentService.findAllDepartment();
        if(departmentList!=null){
            model.addAttribute("departmentList",departmentList);
        }
        return "user/list";
    }

    @GetMapping("/add")
    public String add(){
        return "user/add";
    }

    @PostMapping("showUserTable")
    @ResponseBody
    public TableResponse<UserTable> showUserTable(Integer area_id,Integer department_id,Integer employee_type){

        List<UserTable> userTableList = userService.findUserByAreaIdAndDepartmentIdAndEmployeeType(area_id,department_id,employee_type);

        System.out.println(userTableList.get(0));

        TableResponse<UserTable> userTableResponse = new TableResponse<>();
        int count;
        if(employee_type==0){
            count = userService.countAll();
        }else {
            count = userService.countByAreaIdAndDepartmentId(area_id,department_id);
        }

        userTableResponse.setTotal(count);
        userTableResponse.setData(userTableList);
        return userTableResponse;
    }

}
