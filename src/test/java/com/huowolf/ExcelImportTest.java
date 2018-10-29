package com.huowolf;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.huowolf.dto.UserTable;
import com.huowolf.model.User;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by huowolf on 2018/10/23.
 */
public class ExcelImportTest {

    @Test
    public void importExcel(){
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setNeedSave(true);
        List<UserTable> list = ExcelImportUtil.importExcel(
                new File("C:\\Users\\huowolf\\Desktop\\用户信息.xlsx"),
                UserTable.class, params);
        System.out.println(list.size());
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));

        UserTable userTable = list.get(0);
        File file = new File(userTable.getPhoto());
        System.out.println(file.getName());
    }
}
