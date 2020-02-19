package com.rh.cloudcampus.edu.controller.auth;


import com.rh.cloudcampus.edu.service.auth.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/addMenu")
    public String insertTest(@RequestBody String param) throws Exception {
        return authService.addMenu(param);
    }
}
