package com.huowolf.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.huowolf.dto.UserTable;
import com.huowolf.mapper.UserMapper;
import com.huowolf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huowolf on 2018/10/14.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HashMap<String,Integer> areaMap;

    @Autowired
    private HashMap<String,Integer> departmentMap;

    /**
     * 如果区域ID和部门ID不为空，则根据区域ID和部门ID查询用户，
     * 否则查询所有数据
     * @param areaId
     * @param departmentId
     * @param page
     * @param limit
     * @return
     */
    public List<UserTable> findUserByAreaIdAndDepartmentId(Integer areaId,Integer departmentId,Integer page, Integer limit){

        List<UserTable> userTableList = null;

        //启用分页
        PageHelper.startPage(page,limit);

        if(areaId!=null && departmentId!=null){
            userTableList = userMapper.findUserTableByAreaIdAndDepartmentId(areaId,departmentId);
        }else {
            userTableList = userMapper.findAllUserTable();
        }


        return userTableList;
    }

    /**
     * 获取员工总数
     * @return
     */
    public Integer countAll(){
        return userMapper.selectCount(new User());
    }


    /**
     * 根据区域Id和部门Id获取用户数目
     * @param areaId
     * @param departmentId
     * @return
     */
    public Integer countByAreaIdAndDepartmentId(Integer areaId,Integer departmentId){
        User user = new User();
        user.setAreaId(areaId);
        user.setDepartmentId(departmentId);
        int count = userMapper.selectCount(user);
        return count;
    }


    /**
     * 修改用户
     * @param user
     * @return
     */
    public Boolean editUser(User user){
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i!=0){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }


    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Integer id){
        userMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public UserTable findUserTableById(Integer id){
        return userMapper.findUserTableById(id);
    }


    /**
     *
     * @param searchkey
     * @param searchContent
     * @param areaId
     * @param departmentId
     * @return
     */
    public List<UserTable> searchUserTable(String searchkey,String searchContent,Integer areaId,Integer departmentId){

        List<UserTable> userTableList = null;

        if(areaId!=null && departmentId!=null){
            userTableList = userMapper.searchUserTableWithAreaIdAndDepartmentId(searchkey,searchContent,areaId,departmentId);
        }else{
            userTableList = userMapper.searchUserTable(searchkey,searchContent);
        }

        return userTableList;
    }

    /**
     * 新增用户
     * @param user
     */
    public void add(User user){
        userMapper.insertUseGeneratedKeys(user);
    }


    /**
     * Excel导入用户数据
     * @param inputStream
     * @return
     * @throws Exception
     */
    public Integer excelImport(InputStream inputStream) throws Exception {

        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setNeedSave(true);
        List<UserTable> userTableList = ExcelImportUtil.importExcel(inputStream, UserTable.class, params);
        for(UserTable userTable:userTableList){
            //填充用户表的关联字段
            Integer areaId = areaMap.get(userTable.getArea());
            userTable.setAreaId(areaId);

            Integer departmentId = departmentMap.get(userTable.getDepartment());
            userTable.setDepartmentId(departmentId);

            File photoPath = new File(userTable.getPhoto());
            String photo = photoPath.getName();
            //设置photo为文件名，不加路径
            userTable.setPhoto(photo);
        }

        int count = userMapper.insertList(userTableList);
        return count;
    }

    /**
     * 得到所有用户数据，用于excel导出
     * @return
     */
    public List<UserTable> findAllUserTable(){
        return userMapper.findAllUserTable();
    }
}
