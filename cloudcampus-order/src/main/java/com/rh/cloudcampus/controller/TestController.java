package com.rh.cloudcampus.controller;

import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.response.ObjectResponse;
import com.rh.cloudcampus.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping("/insert")
    public ObjectResponse<TestDTO> insertTest(@RequestBody TestDTO testDTO) {
        return testService.insertTestValue(testDTO);
    }
}
