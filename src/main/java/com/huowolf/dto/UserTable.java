package com.huowolf.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.huowolf.model.User;

/**
 * Created by huowolf on 2018/10/14.
 */
public class UserTable extends User {
    @Excel(name = "区域",orderNum = "10")
    private String area;

    @Excel(name = "部门",orderNum = "20")
    private String department;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public UserTable() {
    }


    public UserTable(Integer id, String name, String number, String telphone, String photo, String description,
                     Integer departmentId, Integer areaId, String area, String department) {
        super(id, name, number, telphone, photo, description, departmentId, areaId);
        this.area = area;
        this.department = department;
    }

    @Override
    public String toString() {
        return "UserTable{" +
                "area='" + area + '\'' +
                ", department='" + department + '\'' +
                "} " + super.toString();
    }
}
