package com.rh.cloudcampus.edu.dao.auth;

import com.rh.cloudcampus.edu.Entity.Menu;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MenuDao {

    boolean addMenu(Menu menu);
}
