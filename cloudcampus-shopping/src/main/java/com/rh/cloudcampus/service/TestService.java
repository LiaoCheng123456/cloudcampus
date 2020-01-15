package com.rh.cloudcampus.service;

import com.rh.cloudcampus.dao.TestMapper;
import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.enums.RspStatusEnum;
import com.rh.cloudcampus.response.ObjectResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public ObjectResponse<TestDTO> insertTestValue(TestDTO testDto) {
        boolean b = testMapper.insertTestValue(testDto);
        ObjectResponse<TestDTO> response = new ObjectResponse<>();
        if (!b){
            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            return response;
        }
        return response;
    }
}
