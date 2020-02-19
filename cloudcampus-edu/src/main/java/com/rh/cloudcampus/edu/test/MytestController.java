package com.rh.cloudcampus.edu.test;


import com.rh.cloudcampus.edu.service.auth.AuthService;

import com.wsp.core.WSPResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("mytest")
public class MytestController {

    @Resource
    private AuthService authService;

    @PostMapping("/test01")
    public String insertTest(@RequestBody String param) throws Exception {
        System.out.println("输入参数："+param);
        String s = authService.addMenu(param);
//        {"code": "200", "msg":"success"}
        WSPResult result = new WSPResult();
//        return "{\"code\": \"200\", \"msg\":\"success\"}";
        return s;
    }
}
