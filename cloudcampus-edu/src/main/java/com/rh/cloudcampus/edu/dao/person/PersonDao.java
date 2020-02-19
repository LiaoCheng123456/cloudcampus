package com.rh.cloudcampus.edu.dao.person;

import com.rh.cloudcampus.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PersonDao {

    boolean insertTestValue(TestDTO testDto);
}
