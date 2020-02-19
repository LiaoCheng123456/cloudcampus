package com.rh.cloudcampus.edu.util.jwt;

import com.alibaba.fastjson.JSON;
import com.rh.cloudcampus.edu.model.PageData;

import com.rh.cloudcampus.edu.util.CommonUtil;
import com.rh.cloudcampus.edu.util.ParameterUtil;
import com.rh.cloudcampus.edu.util.jwt.AuthJwt;
import com.rh.cloudcampus.edu.util.jwt.JwtService;
import com.wsp.core.WSPCode;
import com.wsp.core.WSPResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

//import com.rh.cloudcampus.dao.CommonDao;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    WSPResult wspResult = new WSPResult();
    protected CommonUtil util = new CommonUtil();

    @Autowired
    JwtService jwtService;

//    @Autowired
//    CommonDao commonDao;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的Access注解
        com.rh.cloudcampus.edu.util.jwt.AuthJwt access = method.getAnnotation(AuthJwt.class);
        if (access == null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }

        String jwt = httpServletRequest.getHeader("Authorization");
        if (jwt != null && !jwt.equals("")) {
            //如果是微信公众号的
                String reslut = jwtService.authJwt(jwt);
                if (reslut != null && !reslut.equals("")) {

                    PageData result = JSON.parseObject(reslut, PageData.class);

                    if (!ParameterUtil.getResult(result.get("uid")) && !ParameterUtil.getResult(result.get("useType"))){

                        httpServletRequest.setAttribute("uid", result.get("uid"));

                        httpServletRequest.setAttribute("useType", result.get("useType"));

                        httpServletRequest.setAttribute("institutionId", result.get("institutionId"));

                        httpServletRequest.setAttribute("schoolId", result.get("schoolId"));

                        return true;
                    }
            }
    }
        // 拦截之后应该返回公共结果, 这里没做处理
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(200);
    PrintWriter writer = httpServletResponse.getWriter();
        wspResult.setCode(WSPCode.FAIL_AUTH);
        wspResult.setMsg(WSPCode.FAIL_AUTH_STR);
        writer.print(JSON.toJSONString(wspResult));
        return false;
}

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
