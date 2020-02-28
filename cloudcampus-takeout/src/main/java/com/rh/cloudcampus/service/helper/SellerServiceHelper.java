package com.rh.cloudcampus.service.helper;

import com.rh.cloudcampus.dao.SellerMapper;
import com.rh.cloudcampus.dto.TSeller;
import com.rh.cloudcampus.exception.ParamException;
import com.rh.cloudcampus.utils.Validation;
import com.rh.cloudcampus.enums.ParamCheckEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liaocheng
 * @date: 2020-02-17 15:36
 */
@Component(value = "sellerServiceHelper")
public class SellerServiceHelper {

    @Resource
    private SellerMapper sellerMapper;

    /**
     * 添加时验证信息
     */
    public void checkSeller(TSeller seller) {

        // 验证参数
        if (StringUtils.isEmpty(seller.getUsername())
                || StringUtils.isEmpty(seller.getPassword())
                || seller.getSchoolId() == null
                || seller.getSchoolClassId() == null) {
            throw new ParamException(ParamCheckEnum.PARAM_LACK);
        }

        // 验证账号是否重复
        if (!checkSellerUsernameExists(seller.getUsername())) {
            throw new ParamException(ParamCheckEnum.USERNAME_EXISTS);
        }

        // @TODO
        // 验证学校是否存在

        // @TODO
        // 验证校区是否存在

        // 检查用户名长度
        if (!seller.getUsername().matches(Validation.USERNAME)) {
            throw new ParamException(ParamCheckEnum.USERNAME_LENGTH_FAILED);
        }

        // 验证密码是否为弱口令
        if (!seller.getPassword().matches(Validation.PASSWORD)) {
            throw new ParamException(ParamCheckEnum.WEAK_PASSWORD);
        }

    }

    /**
     * 检查用户名是否存在
     * @param username username
     * @return true or false
     */
    private boolean checkSellerUsernameExists(String username) {
        TSeller seller = new TSeller();
        seller.setUsername(username);
        TSeller sellerResult = sellerMapper.getSeller(seller);
        return sellerResult == null;
    }
}
