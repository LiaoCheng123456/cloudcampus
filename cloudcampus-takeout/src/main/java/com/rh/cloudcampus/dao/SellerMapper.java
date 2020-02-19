package com.rh.cloudcampus.dao;

import com.rh.cloudcampus.dto.TSeller;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@Mapper
public interface SellerMapper {

    /**
     * 添加商户
     *
     * @param seller TSeller
     * @return TSeller
     */
    boolean addSeller(TSeller seller);

    /**
     * 查询商户
     *
     * @param seller TSeller
     * @return TSeller
     */
    TSeller getSeller(TSeller seller);
}
