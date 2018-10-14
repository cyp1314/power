package com.huowolf.service;

import com.huowolf.dto.EmployeeTable;
import com.huowolf.mapper.EmployeeMapper;
import com.huowolf.model.Employee;
import com.huowolf.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huowolf on 2018/10/10.
 */
@Service
public class EmploryeeService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 根据姓名和密码查找员工
     * @param name
     * @param password
     * @return
     */
    public Employee findByNameAndPassword(String name,String password){
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPassword(password);
        Employee result = employeeMapper.selectOne(employee);
        if(result!=null){
            logger.info(result.toString());
        }
        return result;
    }

    public List<EmployeeTable> findAll(){
        return employeeMapper.findAllEmploee();
    }


    /**
     * 获取员工总数
     * @return
     */
    public Integer employeeCount(){
        Employee employee = new Employee();
        employee.setType(new Byte("1"));
        return  employeeMapper.selectCount(employee);
    }


    /**
     * 插入员工
     * @param employee
     * @return
     */
    public Integer add(Employee employee) {
        int i = employeeMapper.insertSelective(employee);
        return i;
    }



    /**
     * 根据员工名字查询用户，查询到返回true,这样做的目的：不允许插入同名员工
     * @param name
     * @return
     */
    public Boolean findByName(String name){
        Employee employee = new Employee();
        employee.setName(name);
        Employee result = employeeMapper.selectOne(employee);
        if(result!=null){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 修改密码
     * @param id
     * @param newpassword
     * @return
     */
    public Boolean editPassword(String id,String newpassword){
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        String pwd = MD5Utils.md5(newpassword);
        employee.setPassword(pwd);
        int i = employeeMapper.updateByPrimaryKeySelective(employee);
        if(i!=0)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
}
