package com.huowolf.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    //文件上传路径
    @Value("${spring.http.multipart.location}")
    private String upload;

    @Autowired
    private HashMap<String,Integer> areaMap;

    @Autowired
    private HashMap<String,Integer> departmentMap;

    @GetMapping("/list")
    public String list(HttpSession session,
                       Model model,
                       @RequestParam(value = "area_id",required = false) Integer areaId,
                       @RequestParam(value = "department_id",required = false) Integer departmentId){
        Employee employee = (Employee) session.getAttribute("loginInfo");

        List<Area> areaList = ServiceUtil.loadAreaByEmployee(areaService,employee);

        if(areaList!=null){
            model.addAttribute("areaList",areaList);
        }

        List<Department> departmentList = departmentService.findAllDepartment();
        if(departmentList!=null){
            model.addAttribute("departmentList",departmentList);
        }

        if(areaId!=null && departmentId!= null){

            Area area = areaService.findAreaById(areaId);
            model.addAttribute("indexArea",area);

            Department department = departmentService.findDepartmentById(departmentId);
            model.addAttribute("indexDepartment",department);
        }
        return "user/list";
    }


    @GetMapping("/toAdd")
    public String add(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("loginInfo");

        List<Area> areaList = ServiceUtil.loadAreaByEmployee(areaService,employee);

        if(areaList!=null){
            model.addAttribute("areaList",areaList);
        }

        List<Department> departmentList = departmentService.findAllDepartment();
        if(departmentList!=null){
            model.addAttribute("departmentList",departmentList);
        }
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


    /**
     * 上传用户照片
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(MultipartFile file) {

        File dir = new File(upload);
        if(!dir.exists()){
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();	//原始名称

        //新的图片名称
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        File newFile = new File(upload+newFileName);

        //将内存中的数据写入磁盘
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            return ResultUtil.error("上传失败");
        }

        HashMap hashMap = new HashMap();
        hashMap.put("src",newFileName);
        return ResultUtil.success("上传成功",hashMap);
    }


    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("add")
    public String add(User user){
        userService.add(user);
        return "redirect:/user/list";
    }


    @PostMapping("importExcel")
    @ResponseBody
    public Result importExcel(MultipartFile file) throws Exception {

        ImportParams params = new ImportParams();
        params.setNeedSave(true);
        List<UserTable> userTableList = ExcelImportUtil.importExcel(file.getInputStream(), UserTable.class, params);
        for(UserTable userTable:userTableList){
            //填充用户表的关联字段
            Integer areaId = areaMap.get(userTable.getArea());
            userTable.setAreaId(areaId);

            Integer departmentId = departmentMap.get(userTable.getDepartment());
            userTable.setDepartmentId(departmentId);
        }
        return null;
    }
}
