package com.huowolf.service;

import com.github.pagehelper.PageHelper;
import com.huowolf.dto.UserQuery;
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
    public List<UserTable> findUserByAreaIdAndDepartmentIdAndEmployeeType(UserQuery userQuery,Integer page, Integer limit){

        Integer area_id = userQuery.getArea_id();
        Integer department_id = userQuery.getDepartment_id();
        Integer employee_type = userQuery.getEmployee_type();
        String search_key = userQuery.getSearch_key();
        String search_content = userQuery.getSearch_content();

        List<UserTable> userTableList = null;

        //启用分页
        PageHelper.startPage(page,limit);

        //如果是搜索查询
        if(search_key!=null && search_content!=null){
            if(employee_type == 0){
                userTableList = userMapper.searchUserTable(search_key,search_content);
            }else{
                userTableList = userMapper.searchUserTableWithAreaIdAndDepartmentId(search_key,search_content,area_id,department_id);
            }
        }else{//否则是数据表格所需全部数据

            //如果是超级管理员，返回全部数据
            if(employee_type == 0){
                userTableList = userMapper.findAllUserTable();
            }else{
                userTableList = userMapper.findUserTableByAreaIdAndDepartmentId(area_id,department_id);
            }
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
}
