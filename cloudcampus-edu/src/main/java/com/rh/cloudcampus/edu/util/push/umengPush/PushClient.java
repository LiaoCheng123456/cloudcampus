package com.rh.cloudcampus.edu.util.push.umengPush;

import com.rh.cloudcampus.edu.util.push.umengPush.UmengNotification;
import com.wsp.core.WSPResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PushClient {

	// The edu agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();

	// The host
	protected static final String host = "http://msg.umeng.com";

	// The upload path
	protected static final String uploadPath = "/upload";

	// The post path
	protected static final String postPath = "/api/send";

	/**
	 * HttpClient锁
	 */
	private static Object httpClientLock = new Object();

	public boolean send(UmengNotification msg) throws Exception {
		synchronized (httpClientLock) {
			String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
			msg.setPredefinedKeyValue("timestamp", timestamp);
			String url = host + postPath;
			String postBody = msg.getPostBody();
			String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
			url = url + "?sign=" + sign;
			HttpPost post = new HttpPost(url);
			post.setHeader("User-Agent", USER_AGENT);
			StringEntity se = new StringEntity(postBody, "UTF-8");
			post.setEntity(se);
			// Send the post request and get the response
			HttpResponse response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			System.out.println("Response Code : " + status);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			try {
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				rd.close();
				post.releaseConnection();
			}
			System.out.println(result.toString());
			if (status == 200) {
				System.out.println("Notification sent successfully.");
			} else {
				System.out.println("Failed to send the notification!");
			}
			return true;
		}
	}

	/**
	 * 
	 * TODO 有返回值的友盟推送.
	 * 
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public WSPResult sendNew(UmengNotification msg) throws Exception {
		synchronized (httpClientLock) {
			WSPResult shjResult = new WSPResult();
			String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
			msg.setPredefinedKeyValue("timestamp", timestamp);
			String url = host + postPath;
			String postBody = msg.getPostBody();
			String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
			url = url + "?sign=" + sign;
			HttpPost post = new HttpPost(url);
			post.setHeader("User-Agent", USER_AGENT);
			StringEntity se = new StringEntity(postBody, "UTF-8");
			post.setEntity(se);
			// Send the post request and get the response
			HttpResponse response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			System.out.println("Response Code : " + status);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			try {
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			} catch (Exception e) {
				shjResult.setMsg(e.toString() + "关闭链接失败");
			} finally {
				rd.close();
				post.releaseConnection();
			}

			System.out.println(result.toString());
			if (status == 200) {
				shjResult.setCode("200");
				shjResult.setMsg("");
			} else {
				shjResult.setCode("204");
				shjResult.setMsg(result.toString());
			}

			return shjResult;
		}
	}

	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey, String appMasterSecret, String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println("uploadContents:" + result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = new JSONObject(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file" + ret);
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue

		return fileId;
	}

}
