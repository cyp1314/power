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

    public void add(User user){
        userMapper.insertUseGeneratedKeys(user);
    }
}
