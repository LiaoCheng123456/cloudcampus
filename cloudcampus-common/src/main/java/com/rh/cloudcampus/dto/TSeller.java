package com.rh.cloudcampus.dto;

import java.io.Serializable;

public class TSeller implements Serializable,Cloneable{
    /** 唯一识别编号;ID */
    private Long id ;
    /** 店铺名;店铺名称 */
    private String shopName ;
    /** 学校id;学校id */
    private Long schoolId ;
    /** 校区id;校区id */
    private Long schoolClassId ;
    /** 用户名;用户名 */
    private String username ;
    /** 密码;密码 */
    private String password ;
    /** 状态;1=店铺关闭，2=店铺开启，3=删除 */
    private String status ;
    /** 创建时间;创建时间 */
    private Long createTime ;
    /** 更新时间;店铺信息更新时间 */
    private Long updateTime ;
    /** 更新人;更新人 */
    private Long updateBy ;

    /** 唯一识别编号;ID */
    public Long getId(){
        return this.id;
    }
    /** 唯一识别编号;ID */
    public void setId(Long id){
        this.id = id;
    }
    /** 店铺名;店铺名称 */
    public String getShopName(){
        return this.shopName;
    }
    /** 店铺名;店铺名称 */
    public void setShopName(String shopName){
        this.shopName = shopName;
    }
    /** 学校id;学校id */
    public Long getSchoolId(){
        return this.schoolId;
    }
    /** 学校id;学校id */
    public void setSchoolId(Long schoolId){
        this.schoolId = schoolId;
    }
    /** 校区id;校区id */
    public Long getSchoolClassId(){
        return this.schoolClassId;
    }
    /** 校区id;校区id */
    public void setSchoolClassId(Long schoolClassId){
        this.schoolClassId = schoolClassId;
    }
    /** 用户名;用户名 */
    public String getUsername(){
        return this.username;
    }
    /** 用户名;用户名 */
    public void setUsername(String username){
        this.username = username;
    }
    /** 密码;密码 */
    public String getPassword(){
        return this.password;
    }
    /** 密码;密码 */
    public void setPassword(String password){
        this.password = password;
    }
    /** 状态;1=店铺关闭，2=店铺开启，3=删除 */
    public String getStatus(){
        return this.status;
    }
    /** 状态;1=店铺关闭，2=店铺开启，3=删除 */
    public void setStatus(String status){
        this.status = status;
    }
    /** 创建时间;创建时间 */
    public Long getCreateTime(){
        return this.createTime;
    }
    /** 创建时间;创建时间 */
    public void setCreateTime(Long createTime){
        this.createTime = createTime;
    }
    /** 更新时间;店铺信息更新时间 */
    public Long getUpdateTime(){
        return this.updateTime;
    }
    /** 更新时间;店铺信息更新时间 */
    public void setUpdateTime(Long updateTime){
        this.updateTime = updateTime;
    }
    /** 更新人;更新人 */
    public Long getUpdateBy(){
        return this.updateBy;
    }
    /** 更新人;更新人 */
    public void setUpdateBy(Long updateBy){
        this.updateBy = updateBy;
    }
}