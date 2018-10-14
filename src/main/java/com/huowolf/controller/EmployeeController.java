package com.huowolf.controller;

import com.huowolf.dto.EmployeeTable;
import com.huowolf.dto.TableResponse;
import com.huowolf.model.Area;
import com.huowolf.model.Department;
import com.huowolf.model.Employee;
import com.huowolf.service.AreaService;
import com.huowolf.service.DepartmentService;
import com.huowolf.service.EmploryeeService;
import com.huowolf.service.ServiceUtil;
import com.huowolf.util.MD5Utils;
import com.huowolf.util.Result;
import com.huowolf.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huowolf on 2018/10/12.
 */
@RequestMapping("/employee")
@Controller
public class EmployeeController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmploryeeService emploryeeService;

    @GetMapping("/list")
    public String list(HttpSession session,Model model){

        Employee employee = (Employee) session.getAttribute("loginInfo");

        List<Area> areaList = ServiceUtil.loadAreaByEmployee(areaService,employee);

        if(areaList!=null){
            model.addAttribute("areaList",areaList);
        }

        List<Department> departmentList = departmentService.findAllDepartment();
        if(departmentList!=null){
            model.addAttribute("departmentList",departmentList);
        }

        return "/employee/list";
    }


    @PostMapping("/showEmployeeTable")
    @ResponseBody
    public TableResponse<EmployeeTable> showEmployeeTable(){
        List<EmployeeTable> employeeList = emploryeeService.findAll();
        Integer count = emploryeeService.employeeCount();
        TableResponse<EmployeeTable> employeeTableResponse = new TableResponse<>();
        employeeTableResponse.setTotal(count);
        employeeTableResponse.setData(employeeList);
        return employeeTableResponse;
    }

    @PostMapping("/add")
    @ResponseBody
    public Result add(String name, String password, String area){

        if(emploryeeService.findByName(name)){
            return ResultUtil.error("不允许添加相同名字的员工");
        }

        Employee employee = new Employee();
        employee.setName(name);
        String pwd = MD5Utils.md5(password);
        employee.setPassword(pwd);
        employee.setAreaId(Integer.parseInt(area));
        employee.setType(new Byte("1"));
        Integer flag= emploryeeService.add(employee);
        if(flag==1){
            return ResultUtil.success();
        }else{
            return ResultUtil.error("保存失败");
        }
    }

    @PostMapping("editPassword")
    @ResponseBody
    public Result editPassword(String id,String newpassword){
        if(emploryeeService.editPassword(id,newpassword)){
            return ResultUtil.success();
        }else{
            return ResultUtil.error("密码修改失败");
        }
    }
}
