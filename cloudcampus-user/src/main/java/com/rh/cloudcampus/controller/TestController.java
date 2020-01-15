package com.rh.cloudcampus.controller;

import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.response.ObjectResponse;
import com.rh.cloudcampus.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping("/insert")
    public ObjectResponse<TestDTO> insertTest(@RequestBody TestDTO testDTO) throws Exception {
        return testService.insertTestValue(testDTO);
    }
}
