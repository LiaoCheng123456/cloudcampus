package com.rh.cloudcampus.service;

import com.rh.cloudcampus.dao.SellerMapper;
import com.rh.cloudcampus.dto.TSeller;
import com.rh.cloudcampus.enums.RspStatusEnum;
import com.rh.cloudcampus.enums.SellerStatusEnum;
import com.rh.cloudcampus.response.ObjectResponse;
import com.rh.cloudcampus.service.helper.SellerServiceHelper;
import com.rh.cloudcampus.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liaocheng
 * @date: 2020-02-17 12:01
 */
@Service(value = "sellerService")
public class SellerService {

    @Resource
    private SellerServiceHelper sellerServiceHelper;

    @Resource
    private SellerMapper sellerMapper;

    /**
     * 添加商户
     * @param seller TSeller
     * @return ObjectResponse
     */
    public ObjectResponse addSeller(TSeller seller) {

        ObjectResponse objectResponse = new ObjectResponse();

        sellerServiceHelper.checkSeller(seller);

        // 密码加密
        seller.setPassword(CommonUtils.md5(seller.getPassword()));

        // 生成id
        seller.setId(CommonUtils.getId());

        // 创建时间
        seller.setCreateTime(CommonUtils.getTimeStamp());

        // 更新时间
        seller.setUpdateTime(CommonUtils.getTimeStamp());

        // 状态
        seller.setStatus(SellerStatusEnum.SHOP_CLOSE.getCode());

        // 添加到数据库
        boolean b = sellerMapper.addSeller(seller);

        if (!b) {
            objectResponse.setStatus(RspStatusEnum.FAIL.getCode());
            objectResponse.setMessage(RspStatusEnum.FAIL.getMessage());
        }

        return objectResponse;
    }
}
