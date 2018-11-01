package com.huowolf;

import com.huowolf.util.ProjectPath;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huowolf on 2018/10/30.
 */
public class ProjectPathTest {


    @Test
    public void pathTest(){
        //自定义追加路径并格式化
        System.out.println(ProjectPath.getRootPath("userImg/test.txt"));
        //获取根目录
        System.out.println(ProjectPath.getRootPath());
    }

    @Test
    public void userdirTest(){
        String userdir = System.getProperty("user.dir");
        System.out.println(userdir);
    }

}
