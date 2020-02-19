package com.rh.cloudcampus.edu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rh.cloudcampus.edu.model.PageData;
import com.wsp.core.WSPCode;
import com.wsp.core.WSPResult;

public class ParameterUtils {

    /**
     * @param fastjson
     */
    private static JSONObject json = new JSONObject();

    /**
     * 验证多个参数是否为空，并返回为空参数
     * @param pageData
     * @param args
     * @return
     */
    public static String checkParam(PageData pageData, String... args) {
        WSPResult wspResult = new WSPResult();
        if (pageData == null) {
            wspResult.setCode(WSPCode.SUCESS_OPERATE);
            wspResult.setMsg(WSPCode.SUCESS_OPERATE_STR);
            return json.toJSONString(wspResult);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("参数丢失");
        for (int i = 0; args != null && i < args.length; i++) {
            if (pageData.get(args[i]) == null) {
                sb.append(String.format("%s,", args[i]));
            }
        }

        if (sb.toString().length() == 4) {
            return null;
        } else {
            wspResult.setCode(WSPCode.SUCESS_OPERATE);
            wspResult.setMsg(sb.toString());
            return json.toJSONString(wspResult);
        }
    }

    /**
     * 分页转换
     * @param start
     * 				起始页
     * @param limit
     * 				结束位置
     * @return
     * 			{@code page[0] 起始页}
     * 			{@code page[1] 结束位置}
     *
     */
    public static Integer[] parsePage(Object start, Object limit) {
        Integer[] page = new Integer[2];

        // 如果页码有一方为null，那么都置为null，因为不能确定取值范围
        if ((start == null || "".equals(start)) || (limit == null || "".equals(limit))) {
            page[0] = null;
            page[1] = null;
            return page;
        }
        Integer s = Integer.parseInt(start.toString());
        Integer e = Integer.parseInt(limit.toString());
        page[0] = s <= 0 ? 0 : (s - 1) * e;
        page[1] = e;
        return page;
    }


    /**
     * 给pageData添加分页信息
     * @return
     * @throws Exception
     */
    public static void addPageInfo(PageData pd) {
        Integer[] pages = parsePage(pd.get("page"), pd.get("limit"));
        pd.put("start", pages[0]);
        pd.put("limit", pages[1]);
    }


    /**
     *
     * @param result
     *  调用服务返回值
     * @param serviceName
     * 服务名
     * @param requestParam
     * 请求参数
     * @return
     * 服务调用返回值信息
     * @throws Exception
     * 服务调用异常信息
     */
    public static PageData requestResultHandle(String result, String serviceName, String requestParam) throws Exception{
        if (result == null) {
            throw new Exception(String.format("%s 服务返回值为 %s, 请求参数为 %s",serviceName, result, requestParam));
        }

        // 转换成HashMap
        PageData pageData = JSON.parseObject(result, PageData.class);
        if (pageData != null && String.valueOf(pageData.get("code")).equals(WSPCode.SUCCESS)) {
            return pageData;
        } else {
            throw new Exception(String.format("%s 服务调用失败，返回值为 %s, 请求参数为 %s", serviceName, result, requestParam));
        }
    }

}
