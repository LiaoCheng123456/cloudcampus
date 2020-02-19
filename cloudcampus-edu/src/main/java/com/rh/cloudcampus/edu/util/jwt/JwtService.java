package com.rh.cloudcampus.edu.util.jwt;


import com.alibaba.fastjson.JSON;
import com.rh.cloudcampus.edu.model.MJwt;
import com.rh.cloudcampus.edu.model.MLoginRedis;
import com.rh.cloudcampus.edu.model.MSub;
import com.rh.cloudcampus.edu.model.Secret;
import com.rh.cloudcampus.edu.util.redis.RedisUtil;
import com.rh.cloudcampus.edu.util.ParameterUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;


@Service("jwtService")
public class JwtService {

    //注入密钥
    @Autowired
    private Secret secret;

    //jwt有效天数
    private static final int JWT_TIME = 30;
    //jwt前缀
    private static final String JWT_PREF = "JWT_";

    /**
     * 验证
     * @return
     */
    
    public String authJwt(String jwt) {
        try {
            if(!ParameterUtil.getResult(jwt)) {
                String jwtJson = parseJWT(jwt);

                MJwt claims = JSON.parseObject(jwtJson, MJwt.class);

                MSub mSub = JSON.parseObject(claims.getSub(), MSub.class);

                String keyId = mSub.getLoginType() + "_" + mSub.getUid().toString();

                Object object = RedisUtil.getKey("JWT_" + keyId);
                if (!ParameterUtil.getResult(object)) {
                    MLoginRedis mLoginRedis = JSON.parseObject(JSON.toJSONString(object), MLoginRedis.class);

                    if (mLoginRedis.getUuid().equals(claims.getJti())) {
                        //TODO 时间未定
                        RedisUtil.setExpire(JWT_PREF + keyId, JWT_TIME);

                        Map<String, Object> re = new HashMap<>();

                        re.put("uid", mSub.getUid());

                        re.put("deviceToken", mLoginRedis.getDeviceToken());

                        re.put("Platform", mLoginRedis.getPlatform());

                        re.put("schoolId", mLoginRedis.getSchoolId());

                        re.put("institutionId", mLoginRedis.getInstitutionId());

                        re.put("useType", mSub.getLoginType());

                        return JSON.toJSONString(re);

                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }else{
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
    /**
     * 存储jwt到redis
     * @return
     */
    
    public void saveJwt(String jwt,String loginType,Long uid,String uuid,String deviceToken,Integer Platform,String institutionId,String schoolId) {
        String keyId = "";
        keyId = loginType+"_"+ uid.toString();
        RedisUtil.remove(JWT_PREF+keyId);
        MLoginRedis mLoginRedis=new MLoginRedis(uuid,jwt,deviceToken,Platform,institutionId,schoolId);
        RedisUtil.setKey(JWT_PREF+keyId,mLoginRedis,JWT_TIME);
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    public SecretKey generalKey(){
        String stringKey = secret.getKey();
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param userName
     * @param
     * @return
     * @throws Exception
     */
    
    public String createJWT(String userName, Long uid,String loginType,String uuid) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey key = generalKey();
        MSub mSub = new MSub();
        mSub.setUid(uid);
        mSub.setLoginType(loginType);
        mSub.setUserName(userName);
        JwtBuilder builder = Jwts.builder()
                .setId(uuid)
                .setSubject(JSON.toJSONString(mSub))
                .signWith(signatureAlgorithm, key)
                .setExpiration(null);
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    
    public String parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return JSON.toJSONString(claims);

    }

}

