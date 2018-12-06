package com.huowolf;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.huowolf.dto.UserTable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    }


    @Test
    public void templateExportTest() throws Exception {
        TemplateExportParams params = new TemplateExportParams(
                "excel/计划生育信息登记表.xls");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "张三");
        map.put("sex", "男");
        map.put("address", "广东省汕头市");
        map.put("id_number", "445122197711012756");

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        FileOutputStream fos = new FileOutputStream("template.xls");
        workbook.write(fos);
        fos.close();
    }
}
