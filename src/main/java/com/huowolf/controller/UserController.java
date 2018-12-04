package com.huowolf.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.huowolf.dto.TableResponse;
import com.huowolf.dto.UserTable;
import com.huowolf.model.Area;
import com.huowolf.model.Department;
import com.huowolf.model.User;
import com.huowolf.service.AreaService;
import com.huowolf.service.DepartmentService;
import com.huowolf.service.UserService;
import com.huowolf.util.Result;
import com.huowolf.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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

    @GetMapping("/list")
    public String list(
            Model model,
            @RequestParam(value = "area_id", required = false) Integer areaId,
            @RequestParam(value = "department_id", required = false) Integer departmentId) {

        if (areaId != null && departmentId != null) {
            Area area = areaService.findAreaById(areaId);
            model.addAttribute("currentArea", area);

            Department department = departmentService.findDepartmentById(departmentId);
            model.addAttribute("currentDepartment", department);
        }
        return "user/list";
    }


    @GetMapping("/toAdd")
    public String add() {
        return "user/add";
    }


    /**
     * 返回用户数据表格所需数据
     *
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
            Integer limit) {


        List<UserTable> userTableList = userService.findUserByAreaIdAndDepartmentId(areaId, departmentId, page, limit);

        TableResponse<UserTable> userTableResponse = new TableResponse<>();

        //返回数据总数
        int count;
        if (areaId != null && departmentId != null) {
            count = userService.countByAreaIdAndDepartmentId(areaId, departmentId);
            if (count == 0) {
                userTableResponse.setCode(-1);
                userTableResponse.setMsg("没有相关数据");
            }
        } else {
            count = userService.countAll();
        }


        userTableResponse.setCount(count);
        userTableResponse.setData(userTableList);

        return userTableResponse;
    }


    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("editUser")
    @ResponseBody
    public Result editUser(User user) {

        Boolean flag = userService.editUser(user);
        if (flag) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error("更新失败");
        }
    }


    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @PostMapping("deleteUser")
    @ResponseBody
    public Result deleteUser(Integer id) {
        userService.deleteUser(id);
        return ResultUtil.success("删除成功", null);
    }


    /**
     * 用户详情页
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("view")
    public String view(Integer id, Model model) {
        UserTable userTable = userService.findUserTableById(id);
        model.addAttribute("user", userTable);
        return "user/view";
    }


    /**
     * 搜索用户
     *
     * @param searchKey
     * @param searchContent
     * @param areaId
     * @param departmentId
     */
    @PostMapping("search")
    @ResponseBody
    public TableResponse<UserTable> search(
            @RequestParam("search_key") String searchKey,
            @RequestParam("search_content") String searchContent,
            @RequestParam("area_id") Integer areaId,
            @RequestParam("department_id") Integer departmentId) {

        List<UserTable> userTableList = userService.searchUserTable(searchKey, searchContent, areaId, departmentId);


        TableResponse<UserTable> userTableResponse = new TableResponse<>();
        userTableResponse.setData(userTableList);

        int count = userTableList.size();
        if (count == 0) {
            userTableResponse.setCode(-1);
            userTableResponse.setMsg("没有相关数据");
        }

        userTableResponse.setCount(count);

        return userTableResponse;
    }


    /**
     * 上传用户照片
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();    //原始名称

        //新的图片名称
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        File newFile = new File(upload + newFileName);

        //将内存中的数据写入磁盘
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            return ResultUtil.error("上传失败");
        }

        HashMap hashMap = new HashMap();
        hashMap.put("src", newFileName);
        return ResultUtil.success("上传成功", hashMap);
    }


    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping("add")
    public String add(User user) {
        userService.add(user);
        return "redirect:/user/list";
    }


    /**
     * 导入用户数据
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("importExcel")
    @ResponseBody
    public Result importExcel(MultipartFile file) throws Exception {

        Integer count = userService.excelImport(file.getInputStream());
        return ResultUtil.success("导入成功", count);
    }


    /**
     * 导出用户信息
     * @param map
     * @param request
     * @param response
     */
    @RequestMapping("exportExcel")
    public void exportExcel(ModelMap map, HttpServletRequest request, HttpServletResponse response){

        List<UserTable> allUserTable = userService.findAllUserTable();

        ExportParams params = new ExportParams("用户信息", "sheet1", ExcelType.XSSF);
        //params.setFreezeCol(1);
        map.put(NormalExcelConstants.DATA_LIST, allUserTable);
        map.put(NormalExcelConstants.CLASS, UserTable.class);
        map.put(NormalExcelConstants.PARAMS, params);
        map.put(NormalExcelConstants.FILE_NAME,"用户信息");
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }
}
