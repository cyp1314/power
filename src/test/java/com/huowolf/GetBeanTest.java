package com.huowolf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * Created by huowolf on 2018/10/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetBeanTest {

    @Autowired
    private HashMap areaMap;

    @Autowired
    private HashMap departmentMap;

    @Test
    public void testGetAreaMap(){
        System.out.println(areaMap.toString());
    }

    @Test
    public void testGetDepartmentMap(){
        System.out.println(departmentMap.toString());
    }
}
