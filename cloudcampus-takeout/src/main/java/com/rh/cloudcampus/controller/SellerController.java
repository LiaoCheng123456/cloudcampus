package com.rh.cloudcampus.controller;

import com.rh.cloudcampus.dto.TSeller;
import com.rh.cloudcampus.response.ObjectResponse;
import com.rh.cloudcampus.service.SellerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author liaocheng
 * @date: 2020-02-17 11:57
 */
@RestController
@RequestMapping("seller")
public class SellerController {

    @Resource(name = "sellerService")
    private SellerService sellerService;

    @PostMapping(value = "addseller")
    public ObjectResponse addSeller(HttpServletRequest request, @RequestBody TSeller seller) {
        return sellerService.addSeller(seller);
    }
}
