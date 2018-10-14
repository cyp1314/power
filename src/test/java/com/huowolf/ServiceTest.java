package com.huowolf;

import com.huowolf.service.EmploryeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by huowolf on 2018/10/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private EmploryeeService emploryeeService;

    @Test
    public void testEmployeeCount(){
        Integer count = emploryeeService.employeeCount();
        System.out.println(count);
    }
}
