package com.rh.cloudcampus.dao;

import com.rh.cloudcampus.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    boolean insertTestValue(TestDTO testDto);
}
