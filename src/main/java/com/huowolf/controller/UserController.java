package com.huowolf.controller;

import com.huowolf.dto.TableResponse;
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
import org.springframework.web.bind.annotation.*;

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
    public String list(HttpSession session, Model model,Integer areaId,Integer departmentId){
        Employee employee = (Employee) session.getAttribute("loginInfo");
        model.addAttribute("employee",employee);

        List<Area> areaList = ServiceUtil.loadAreaByEmployee(areaService,employee);

        if(areaList!=null){
            model.addAttribute("areaList",areaList);
        }

        List<Department> departmentList = departmentService.findAllDepartment();
        if(departmentList!=null){
            model.addAttribute("departmentList",departmentList);
        }

        if(areaId!=null && departmentId!= null){
            model.addAttribute("areaId",areaId);
            model.addAttribute("departmentId",departmentId);
        }
        return "user/list";
    }

    @GetMapping("/add")
    public String add(){
        return "user/add";
    }


    /**
     * 返回用户数据表格所需数据
     * @param areaId
     * @param departmentId
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("showUserTable")
    @ResponseBody
    public TableResponse<UserTable> showUserTable(
            @RequestParam("area_id") Integer areaId,
            @RequestParam("department_id") Integer departmentId,
            Integer page,
            Integer limit){


        List<UserTable> userTableList = userService.findUserByAreaIdAndDepartmentId(areaId,departmentId,page,limit);

        TableResponse<UserTable> userTableResponse = new TableResponse<>();

        //返回数据总数
        int count;
        if(areaId!=null && departmentId!=null){
            count = userService.countByAreaIdAndDepartmentId(areaId,departmentId);
            if(count==0){
                userTableResponse.setCode(-1);
                userTableResponse.setMsg("没有相关数据");
            }
        }else {
            count = userService.countAll();
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


    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("deleteUser")
    @ResponseBody
    public Result deleteUser(Integer id){
        System.out.println("id:"+id);
        userService.deleteUser(id);
        return ResultUtil.success("删除成功",null);
    }


    /**
     * 用户详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("view")
    public String  view(Integer id, Model model){
        UserTable userTable = userService.findUserTableById(id);
        model.addAttribute("user",userTable);
        return "user/view";
    }


    /**
     * 搜索用户
     * @param searchKey
     * @param searchContent
     * @param areaId
     * @param departmentId
     */
    @PostMapping("search")
    @ResponseBody
    public TableResponse<UserTable> search(
            @RequestParam("search_key") String searchKey,
            @RequestParam("search_content")String searchContent,
            @RequestParam("area_id") Integer areaId,
            @RequestParam("department_id") Integer departmentId){

        List<UserTable> userTableList = userService.searchUserTable(searchKey, searchContent, areaId, departmentId);


        TableResponse<UserTable> userTableResponse = new TableResponse<>();
        userTableResponse.setData(userTableList);

        int count = userTableList.size();
        if(count==0){
            userTableResponse.setCode(-1);
            userTableResponse.setMsg("没有相关数据");
        }

        userTableResponse.setCount(count);

        return userTableResponse;
    }
}
