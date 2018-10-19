package com.huowolf.controller;

import com.huowolf.dto.TableResponse;
import com.huowolf.dto.UserQuery;
import com.huowolf.dto.UserTable;
import com.huowolf.model.Area;
import com.huowolf.model.Department;
import com.huowolf.model.Employee;
import com.huowolf.model.User;
import com.huowolf.service.AreaService;
import com.huowolf.service.DepartmentService;
import com.huowolf.service.ServiceUtil;
import com.huowolf.service.UserService;
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

    /**
     * 返回用户数据表格所需数据
     * @param userQuery
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("showUserTable")
    @ResponseBody
    public TableResponse<UserTable> showUserTable(UserQuery userQuery,Integer page, Integer limit){
        Integer area_id = userQuery.getArea_id();
        Integer department_id = userQuery.getDepartment_id();
        Integer employee_type = userQuery.getEmployee_type();
        String search_key = userQuery.getSearch_key();
        String search_content = userQuery.getSearch_content();

        List<UserTable> userTableList = userService.findUserByAreaIdAndDepartmentIdAndEmployeeType(userQuery,page,limit);

        TableResponse<UserTable> userTableResponse = new TableResponse<>();

        //返回数据总数
        int count;
        if(employee_type==0){
            count = userService.countAll();
        }else {
            count = userService.countByAreaIdAndDepartmentId(area_id,department_id);
        }

        if(search_key!=null && search_content!=null){
           count = userTableList.size();
           if(count==0){
               userTableResponse.setCode(-1);
               userTableResponse.setMsg("没有相关数据");
           }
        }

        userTableResponse.setCount(count);
        userTableResponse.setData(userTableList);

        return userTableResponse;
    }


    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("editUser")
    @ResponseBody
    public Result editUser(User user){

        Boolean flag = userService.editUser(user);
        if(flag){
            return ResultUtil.success();
        }else{
            return ResultUtil.error("更新失败");
        }
    }


    @PostMapping("deleteUser")
    @ResponseBody
    public Result deleteUser(Integer id){
        System.out.println("id:"+id);
        userService.deleteUser(id);
        return ResultUtil.success("删除成功",null);
    }


    @GetMapping("view")
    public String  view(Integer id, Model model){
        UserTable userTable = userService.findUserTableById(id);
        model.addAttribute("user",userTable);
        return "user/view";
    }
}
