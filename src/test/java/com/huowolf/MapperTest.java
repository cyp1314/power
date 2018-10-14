package com.huowolf;

import com.huowolf.dto.EmployeeTable;
import com.huowolf.mapper.EmployeeMapper;
import com.huowolf.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by huowolf on 2018/10/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void TestFindAllEmploee(){
        List<EmployeeTable> allEmploee = employeeMapper.findAllEmploee();
        System.out.println(allEmploee.get(0));
        assert allEmploee.size()==1;
    }

    @Test
    public void testEmployeeCount(){
        Integer count=employeeMapper.selectCount(new Employee());
        System.out.println(count);
    }
}
