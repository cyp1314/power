package com.huowolf.controller;


import com.huowolf.model.Employee;
import com.huowolf.service.EmploryeeService;
import com.huowolf.util.MD5Utils;
import com.huowolf.util.Result;
import com.huowolf.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by huowolf on 2018/10/10.
 */
@Controller
@CacheConfig(cacheNames = "content")
public class LoginController {

    @Autowired
    private EmploryeeService emploryeeService;

    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password, HttpSession httpSession){
        String newpwd= MD5Utils.md5(password);
        Employee employee = emploryeeService.findByNameAndPassword(username,newpwd);
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
}
