package com.huowolf.dto;

/**
 * Created by huowolf on 2018/10/17.
 */
public class UserQuery {
    private Integer area_id;
    private Integer department_id;
    private Integer employee_type;
    private String search_key;
    private String search_content;

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public Integer getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(Integer employee_type) {
        this.employee_type = employee_type;
    }

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }

    public String getSearch_content() {
        return search_content;
    }

    public void setSearch_content(String search_content) {
        this.search_content = search_content;
    }
}
