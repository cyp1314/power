package com.huowolf.controller;


import com.huowolf.model.Employee;
import com.huowolf.service.EmploryeeService;
import com.huowolf.util.MD5Utils;
import com.huowolf.util.Result;
import com.huowolf.util.ResultUtil;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by huowolf on 2018/10/10.
 */
@Controller
@CacheConfig(cacheNames = "content")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmploryeeService emploryeeService;

    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password, String code, HttpSession httpSession){

        // 获取session中的验证码
        String sessionCode = (String) httpSession.getAttribute("captcha");
        // 判断验证码
        if (code==null || !sessionCode.equals(code.trim())) {
            return ResultUtil.error("验证码不正确");
        }

        String newPwd= MD5Utils.md5(password);
        Employee employee = emploryeeService.findByNameAndPassword(username,newPwd);
        if(employee==null){
            return ResultUtil.error("用户名或密码不正确");
        }else{
            httpSession.setAttribute("loginInfo",employee);
            return ResultUtil.success();
        }
    }

    @GetMapping("/logout")
//    @CacheEvict(allEntries = true)
    public String logout(HttpSession session){
        session.removeAttribute("loginInfo");
        session.invalidate();
        return "redirect:/tologin";
    }


    /**
     * 显示验证码
     * @param response
     * @throws IOException
     */
    @RequestMapping("/kaptcha")
    public void kaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response);

        // 三个参数分别为宽、高、位数
        ChineseCaptcha chineseCaptcha = new ChineseCaptcha(110, 48, 4);

        // 设置字体
        //chineseCaptcha.setFont(new Font("楷体", Font.PLAIN, 28));  // 有默认字体，可以不用设置

        // 生成的验证码
        String code = chineseCaptcha.text();
        logger.info("生成的验证码："+code);

        // 验证码存入session
        request.getSession().setAttribute("captcha", code);

        // 输出图片流
        chineseCaptcha.out(response.getOutputStream());

        response.getOutputStream().close();
    }
}
