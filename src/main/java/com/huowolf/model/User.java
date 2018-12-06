package com.huowolf.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.persistence.*;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "工号")
    private String number;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "身份证号",width = 20)
    private String idNumber;

    @Excel(name = "籍贯",width = 20)
    private String address;

    @Excel(name = "电话",width = 15)
    private String telphone;

    //@Excel(name = "照片",type = 2 ,width = 40 , height = 30)
    private String photo;

    @Excel(name = "备注",width = 20)
    private String description;


    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "area_id")
    private Integer areaId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * @return photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return department_id
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return area_id
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User() {
    }

    public User(String name, String number, String sex, String idNumber, String address, String telphone, String photo, String description, Integer departmentId, Integer areaId) {
        this.name = name;
        this.number = number;
        this.sex = sex;
        this.idNumber = idNumber;
        this.address = address;
        this.telphone = telphone;
        this.photo = photo;
        this.description = description;
        this.departmentId = departmentId;
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", sex='" + sex + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", address='" + address + '\'' +
                ", telphone='" + telphone + '\'' +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", departmentId=" + departmentId +
                ", areaId=" + areaId +
                '}';
    }
}