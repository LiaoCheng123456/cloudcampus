package com.rh.cloudcampus.edu.util.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rh.cloudcampus.edu.util.wx.WxConsts;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WxAddNewsUtil {
    private static  Map<String,Object> ACCESS_TOKEN = new ConcurrentHashMap();


    public static String getAccessToken() {
        if(ACCESS_TOKEN.isEmpty()){
            getToken();
        }

        long getTime = (long) ACCESS_TOKEN.get("getTime");

        int expires_in = (int) ACCESS_TOKEN.get("expires_in");

        long nowTime = System.currentTimeMillis()/1000;

        if(nowTime - getTime >= expires_in - 10 ){
            getToken();
        }


        return JSON.toJSONString(ACCESS_TOKEN);
    }

    private static void getToken(){

//      String param = "grant_type=" + "client_credential" + "&appid=" + "wx0dd4cd0a145e1229" + "&secret=" + "a75fdbdd02306eabb6cc4afadf4d34ff";

        String param = "grant_type=client_credential" + "&appid=" + com.rh.cloudcampus.edu.util.wx.WxConsts.APPID+ "&secret=" + com.rh.cloudcampus.edu.util.wx.WxConsts.SECRET;
        //获取接口凭证
        String sendGet = WxAddNewsUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token", param);
        System.out.println("发送ACCESS_TOKEN请求的返回值:" + sendGet);
        JSONObject json = JSONObject.parseObject(sendGet);
        String access_token = json.getString("access_token"); //凭证
        ACCESS_TOKEN.put("access_token",access_token);
        System.out.println("获取的access_token:" + access_token);
        int expires_in = json.getInteger("expires_in"); //凭证有效时间，单位：秒
        ACCESS_TOKEN.put("expires_in",expires_in);
        ACCESS_TOKEN.put("getTime",System.currentTimeMillis()/1000);
        System.out.println("有效的时间:" + expires_in);
        System.out.print("获取凭证成功");
        System.out.println("返回的数据:" + access_token);
    }


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            System.out.println("发送的链接请求:" + urlNameString);
            URL reaurl = new URL(urlNameString);

            URLConnection connection = reaurl.openConnection();

            //设置通用
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            //建立实际的连接
            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();
            //定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     *      * 向指定 URL 发送POST方法的请求
     *      * @param url 发送请求的 URL
     *      * @param param 参数
     *      * @return String 所代表远程资源的响应结果
     *      
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 上传其他永久素材(图片素材的上限为5000，其他类型为1000)
     *
     * @return
     * @throws Exception
     */
    public static JSONObject addMaterialEver(MultipartFile file, String type, String token) {
        try {
            //File file = new File(fileurl);
            //上传素材https://api.weixin.qq.com/cgi-bin/material/add_material?
            //新增永久素材
            String path = WxConsts.ADD_IMAGE + token + "&type=" + type;
            //新增临时素材
            //String path = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + token + "&type=" + type;

            System.out.println("path:" + path);
            String result = connectHttpsByPost(path, null, file);
            System.out.println("result111:" + result);
            result = result.replaceAll("[\\\\]", "");
            System.out.println("result:" + result);
            JSONObject resultJSON = JSONObject.parseObject(result);
            if (resultJSON != null) {
                if (resultJSON.get("media_id") != null) {
                    System.out.println("上传" + type + "永久素材成功");
                    return resultJSON;
                } else {
                    System.out.println("上传" + type + "永久素材失败");
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static  String connectHttpsByPost(String path, String KK, MultipartFile file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL urlObj = new URL(path);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        String result = null;
        con.setDoInput(true);

        con.setDoOutput(true);

        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="
                        + BOUNDARY);

        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition:form-data;name=\"media\";filelength=\"" + file.getSize() + "\";filename=\""

                + file.getOriginalFilename() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        System.out.println(sb);


        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(file.getInputStream());
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }



    /**
     * 上传微信视频专用
     * @param url
     * @param filePath
     * @param title
     * @param introduction
     * @return
     */
    public static String postFile(String url, String filePath, String title,String introduction) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        String result = null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            conn.setRequestProperty("Cache-Control", "max-age=0");
            String boundary = "-----------------------------"+ System.currentTimeMillis();
            conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
//output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n",file.getName()).getBytes());
            output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"; filelength=\"%s\"\r\n",file.getName(),file.length()).getBytes());
            output.write("Content-Type: video/mp4 \r\n\r\n".getBytes());
            byte[] data = new byte[1024];
            int len = 0;
            FileInputStream input = new FileInputStream(file);
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
            output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", title,introduction).getBytes());
            output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();
            InputStream resp = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            while ((len = resp.read(data)) > -1)
                sb.append(new String(data, 0, len, "utf-8"));
            resp.close();
            result = sb.toString();
            System.out.println(result);
        } catch (Exception e) {
            //logger.error("postFile，不支持http协议", e);
        }
        //logger.info("result="+result);
        return result;
    }






    public static void main(String[] args) {
//        try {
//
//            System.out.println("---"+ACCESS_TOKEN.isEmpty()+System.currentTimeMillis()+"------------"+ WSPDate.getCurrentTimestemp()+"--------------"+System.currentTimeMillis()/1000
//            );
//            PageData pd = new PageData();
//            pd.put("title",123);
//            pd.put("thumbMediaId",123);
//            pd.put("author",123);
//
//            String param = "";
//            param = param +  "{\n" +
//                    "\"articles\": [{\n" +
//                    "\"title\": \"";
//            param = param +pd.get("title");
//            param = param +"\",\n" +
//                    "\"thumb_media_id\":\"";
//            param = param	+pd.get("thumbMediaId");
//            param = param +"\",\n" +
//                    "\"author\": \"";
//            param = param +pd.get("author");
//            param = param	+"\",\n" +
//                    "\"digest\": \"\"+pd.get(\"digest\")+\"\",\n" +
//                    "\"show_cover_pic\": 1,\n" +
//                    "\"content\":\"\"+pd.get(\"content\")+\"\",\n" +
//                    "\"content_source_url\":\"+pd.get(\"contentSourceUrl\")+\",\n" +
//                    "\"need_open_comment\":1,\n" +
//                    "\"only_fans_can_comment\":0\n" +
//                    "},\n" +
//                    "]\n" +
//                    "}" ;
//            //String AppID = "wxd3b299b48dcc33a3e";
//            //String AppSecret = "5acd4fa33c981041ec367ecf0c634ec38";
//            //AccessToken token = getAccessToken(AppID, AppSecret);
//            //String path = "/Users/zhangli/Desktop/test.jpeg";
//            //JSONObject object = addMaterialEver(path,"thumb","17_wYLxfIBVcsEE0jlI7FEVuqHNlRh-yeFv4ndwrbZhL3O5Mtf0jIowvijMA1cUI85e7TUUH-Ii9DPMCg-k7wO0PcfNvFwdIWHUwUA-pmGcLe54jZ_t8uMQXUyqEWDicy9xnt4yFIF1SUxNqrvNWHZgADAHSK");
//            System.out.println(param);
//
//
//        } catch (Exception e) {
//            System.out.println("---");
//        }

        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        long lt = 1547708611;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        System.out.println(res);


    }
}
