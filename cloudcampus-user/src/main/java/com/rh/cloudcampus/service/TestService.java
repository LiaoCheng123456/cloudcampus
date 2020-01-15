package com.rh.cloudcampus.service;

import com.rh.cloudcampus.dao.TestMapper;
import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.enums.RspStatusEnum;
import com.rh.cloudcampus.response.ObjectResponse;
import org.springframework.stereotype.Service;
import service.order.IOrderService;
import service.shopping.IShoppingService;
import service.takeout.ITakeOutService;

import javax.annotation.Resource;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    @Resource
    private IShoppingService shoppingService;

    @Resource
    private IOrderService orderService;

    @Resource
    private ITakeOutService takeOutService;

    public ObjectResponse<TestDTO> insertTestValue(TestDTO testDto) {

        // 修改超市数据，减少库存等等
        TestDTO shopping = new TestDTO();
        shopping.setId(1l);
        shopping.setTest("shopping");
        ObjectResponse<TestDTO> shoppingResult = shoppingService.insertTest(shopping);

        // 修改外卖数据，减少库存等等
        TestDTO takeout = new TestDTO();
        takeout.setId(2l);
        takeout.setTest("takeout");
        ObjectResponse<TestDTO> takeoutResult = orderService.insertTest(takeout);

        // 创建订单
        TestDTO order = new TestDTO();
        order.setId(3l);
        order.setTest("order");
        ObjectResponse<TestDTO> orderResult = takeOutService.insertTest(order);

        // 修改用户数据
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