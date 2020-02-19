package com.rh.cloudcampus.edu.service.auth;

import com.alibaba.fastjson.JSON;
import com.rh.cloudcampus.edu.dao.auth.MenuDao;
import com.rh.cloudcampus.edu.Entity.Menu;

import com.wsp.core.WSPResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService {

    @Resource
    private MenuDao menuDao;


//    @GlobalTransactional(timeoutMills = 300000, name = "edu-gts-seata-example")
    public String addMenu(String param) throws Exception {
        System.out.println("输入参数2"+param);

        Menu menu = new Menu();
        menu.setName("测试菜单");
        menu.setParentId(0);
        menu.setUrl("/menu/testmenu");
        menu.setAddBy(123);

        boolean b = menuDao.addMenu(menu);
        System.out.println("添加结果："+b);

        WSPResult result = new WSPResult();
        if(!b) {
            result.setCode("400");
            result.setMsg("添加失败！");
        }
        return JSON.toJSONString(result);
    }
}
