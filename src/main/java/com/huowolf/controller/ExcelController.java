package com.huowolf.controller;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.huowolf.model.User;
import com.huowolf.service.UserService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huowolf on 2018/12/2.
 */
@Controller
public class ExcelController {

    @Autowired
    private UserService userService;


    @Value("${excel.info.path}")
    private String excelpath;

    /**
     * 打印员工生育信息登记表
     *
     * @param response
     * @throws IOException
     * @throws InvalidFormatException
     */
    @RequestMapping("exceltohtml")
    public void exceltoHtml(HttpServletResponse response, Integer id) throws Exception {


        User user = userService.findUserTableById(id);
        //先生成excel
        String filepath = this.templateExport(user);

        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile(excelpath+filepath)), true, "yes");
        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
        String change = "<script>document.getElementsByTagName('table')[0].style.margin='0 auto';</script>";
        change +="<script>window.print()</script>";
        response.getOutputStream().write(change.getBytes());
        response.getOutputStream().close();
    }


    /**
     * 下载员工计划生育登记表
     * @param response
     * @throws IOException
     */
    @RequestMapping("downloadexcel")
    public void downloadexcel(HttpServletResponse response,Integer id) throws IOException {
        TemplateExportParams params = new TemplateExportParams("excel/计划生育信息登记表.xls");

        User user = userService.findUserTableById(id);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", user.getName()!=null? user.getName() : "");
        map.put("sex", user.getSex()!=null? user.getSex() : "");
        map.put("address", user.getAddress()!=null? user.getAddress() : "");
        map.put("id_number", user.getIdNumber()!=null? user.getIdNumber() : "");

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        String fileName = URLEncoder.encode("计划生育信息登记表.xls", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();

        workbook.write(out);
        out.close();

    }

    /**
     * 将excel模板填充数据，保存到磁盘中
     *
     * @param user
     * @return 文件路径
     * @throws Exception
     */
    private String templateExport(User user) throws Exception {


        String idNumber;
        if(user.getIdNumber()==null){
             idNumber = (int)(Math.random()*(9999-1000+1)+1000)+"";
        }else{
            idNumber = user.getIdNumber();
        }

        String fileName = user.getName() + user.getId()+ idNumber + ".xls";

        File exceldir = new File(excelpath);
        if(!exceldir.exists()){
            exceldir.mkdirs();
        }

        File file = new File(excelpath+fileName);
        if (!file.exists()) {

            TemplateExportParams params = new TemplateExportParams("excel/计划生育信息登记表.xls");
            Map<String, Object> map = new HashMap<String, Object>();
            //解决没值填充就报错
            map.put("name", user.getName()!=null? user.getName() : "");
            map.put("sex", user.getSex()!=null? user.getSex() : "");
            map.put("address", user.getAddress()!=null? user.getAddress() : "");
            map.put("id_number", user.getIdNumber()!=null? user.getIdNumber() : "");

            Workbook workbook = ExcelExportUtil.exportExcel(params, map);

            FileOutputStream fos = new FileOutputStream(excelpath + fileName);
            workbook.write(fos);
            fos.close();
        }

        return fileName;
    }
}
