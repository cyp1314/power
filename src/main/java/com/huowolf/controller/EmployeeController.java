package com.huowolf.controller;

import com.huowolf.dto.EmployeeTable;
import com.huowolf.dto.TableResponse;
import com.huowolf.model.Area;
import com.huowolf.model.Employee;
import com.huowolf.service.AreaService;
import com.huowolf.service.EmploryeeService;
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

import java.util.List;

/**
 * Created by huowolf on 2018/10/12.
 */
@RequestMapping("/employee")
@Controller
public class EmployeeController {

    @Autowired
    private EmploryeeService emploryeeService;

    @Autowired
    private AreaService areaService;

    /**
     * 员工列表
     * @return
     */
    @GetMapping("/list")
    public String list(Model model){
        List<Area> areaList = areaService.findAllArea();
        model.addAttribute("areaList",areaList);
        return "employee/list";
    }


    /**
     * 展示员工表格
     * @return
     */
    @PostMapping("/showEmployeeTable")
    @ResponseBody
    public TableResponse<EmployeeTable> showEmployeeTable(){
        List<EmployeeTable> employeeTableList = emploryeeService.findAll();
        Integer count = emploryeeService.employeeCount();
        TableResponse<EmployeeTable> employeeTableResponse = new TableResponse<>();
        employeeTableResponse.setCount(count);
        employeeTableResponse.setData(employeeTableList);
        return employeeTableResponse;
    }


    /**
     * 添加员工
     * @param name
     * @param password
     * @param area
     * @return
     */
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

        if(emploryeeService.add(employee)){
            return ResultUtil.success();
        }else{
            return ResultUtil.error("保存失败");
        }
    }


    /**
     * 修改员工密码
     * @param id
     * @param newpassword
     * @return
     */
    @PostMapping("editPassword")
    @ResponseBody
    public Result editPassword(String id,String newpassword){
        if(emploryeeService.editPassword(id,newpassword)){
            return ResultUtil.success();
        }else{
            return ResultUtil.error("密码修改失败");
        }
    }


    /**
     * 删除员工
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public Result delete(Integer id){
        emploryeeService.delete(id);
        return ResultUtil.success("删除成功",null);
    }
}
