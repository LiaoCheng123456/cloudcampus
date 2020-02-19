package com.rh.cloudcampus.edu.model;

public class MLoginRedis {

    private String  Uuid;

    private String jwt;

    private String institutionId;

    private String schoolId;

    public String getInstitutionId() {
        return institutionId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getPlatform() {
        return Platform;
    }

    public void setPlatform(Integer platform) {
        Platform = platform;
    }

    private String  deviceToken;

    private Integer Platform;

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String Uuid) {
        this.Uuid = Uuid;
    }

    public MLoginRedis(String uuid, String jwt, String deviceToken, Integer platform,String institutionId,String schoolId) {
        this.Uuid = uuid;
        this.jwt = jwt;
        this.deviceToken = deviceToken;
        this.Platform = platform;
        this.institutionId = institutionId;
        this.schoolId = schoolId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public MLoginRedis() {
    }
}
