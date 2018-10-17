package com.huowolf.service;

import com.github.pagehelper.PageHelper;
import com.huowolf.dto.UserTable;
import com.huowolf.mapper.UserMapper;
import com.huowolf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huowolf on 2018/10/14.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 如果是超级管理员，则获取所有用户
     * 否则，根据区域Id和部门Id获取用户
     * @param areaId
     * @param departmentId
     * @param employeeType
     * @return
     */
    public List<UserTable> findUserByAreaIdAndDepartmentIdAndEmployeeType(Integer areaId,Integer departmentId,Integer employeeType,Integer page,Integer limit){

        List<UserTable> userTableList = null;

        //启用分页
        PageHelper.startPage(page,limit);

        if(employeeType == 0){
            userTableList = userMapper.findAllUserTable();
        }else{
            userTableList = userMapper.findUserTableByAreaIdAndDepartmentId(areaId,departmentId);
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

    public void deleteUser(Integer id){
        userMapper.deleteByPrimaryKey(id);
    }
}
