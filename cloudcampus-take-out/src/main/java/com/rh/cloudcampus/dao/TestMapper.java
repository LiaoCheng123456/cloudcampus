package com.rh.cloudcampus.dao;

import com.rh.cloudcampus.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@Mapper
public interface TestMapper {

    boolean insertTestValue(TestDTO testDto);
}
