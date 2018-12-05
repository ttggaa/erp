package com.stosz.tms.utils;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.StringUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	private static PoolingHttpClientConnectionManager cm = null;

	private static final int connectTimeout = 10000;// 连接超时时间

	private static final int requestTimeout = 5000;// 从socket连接池取连接的超时时间

	private static final int socketTimeout = 20000;// 读取数据的超时时间

	static {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			});
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", new PlainConnectionSocketFactory()).register("https", sslsf).build();
			cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(200);// max connection
			cm.setDefaultMaxPerRoute(50);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	private static CloseableHttpClient getHttpClient() {
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTimeout).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).setConnectionManager(cm).build();
		return httpClient;
	}

	private static void release(HttpRequestBase httpRequestBase, CloseableHttpResponse httpResponse) {
		if (httpRequestBase != null) {
			try {
				httpRequestBase.releaseConnection();
			} catch (Exception e) {
				logger.error("release() Exception={}", e);
			}
		}
		if (httpResponse != null) {
			try {
				httpResponse.close();
			} catch (IOException e) {
				logger.error("release() Exception={}", e);
			}
		}
	}

	/**
	 * 以 ContentType.APPLICATION_FORM_URLENCODED 请求接口
	 * @param postAction 请求地址
	 * @param parameters 请求的参数
	 * @param charset  编码格式
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String postAction, Map<String, Object> parameters, String charset) throws Exception {
		HttpPost httpPost = new HttpPost(postAction);
		List<NameValuePair> valuePairs = new ArrayList<>();
		parameters.entrySet().forEach(item -> {
			String value = item.getValue() == null ? "" : String.valueOf(item.getValue());
			valuePairs.add(new BasicNameValuePair(item.getKey(), value));
		});
		httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, charset));
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpResponse.getEntity(), charset);
			}
		} finally {
			release(httpPost, httpResponse);
		}
		return null;
	}

	/**
	 * @param postAction 请求地址
	 * @param requestBody 请求数据Body
	 * @param contentType 请求类型
	 * @param charset    编码 UTF-8
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String postAction, String requestBody, ContentType contentType, String charset) throws Exception {
		HttpPost httpPost = new HttpPost(postAction);
		httpPost.setEntity(new StringEntity(requestBody, contentType.withCharset(charset)));
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpResponse.getEntity(), charset);
			}
		} finally {
			release(httpPost, httpResponse);
		}
		return null;
	}

	/**
	 * @param postAction  请求地址
	 * @param requestBody 请求数据Body
	 * @param contentType 请求类型
	 * @param charset    编码 UTF-8
	 * @param headers  请求的头部信息
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String postAction, String requestBody, ContentType contentType, String charset, Map<String, String> headers) throws Exception {
		HttpPost httpPost = new HttpPost(postAction);
		if (ArrayUtils.isNotEmpty(headers)) {
			headers.entrySet().stream().forEach(item -> {
				httpPost.addHeader(item.getKey(), item.getValue());
			});
		}
		httpPost.setEntity(new StringEntity(requestBody, contentType.withCharset(charset)));
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getEntity() != null)
				return EntityUtils.toString(httpResponse.getEntity(), charset);
		} finally {
			release(httpPost, httpResponse);
		}
		return null;
	}

	/**
	 * 发送一个 GET 请求
	 * @param url
	 * @param charset
	 * @param connTimeout 建立链接超时时间,毫秒.
	 * @param readTimeout 响应超时时间,毫秒.
	 * @return
	 * @throws ConnectTimeoutException 建立链接超时
	 * @throws SocketTimeoutException  响应超时
	 * @throws Exception
	 */
	public static String get(String url, String charset) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse != null)
				return EntityUtils.toString(httpResponse.getEntity(), charset);
		} finally {
			release(httpGet, httpResponse);
		}
		return null;
	}

	/**
	 * 发送一个 GET 请求
	 * @param url
	 * @param paramMap
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> paramMap, String charset) throws Exception {
		if (!url.endsWith("?")) {
			url = StringUtil.concat(url, "?");
		}
		StringBuilder builder = new StringBuilder(url);
		if (ArrayUtils.isNotEmpty(paramMap)) {
			for (String key : paramMap.keySet()) {
				builder.append(StringUtil.concat(key, "=", paramMap.get(key)));
				builder.append("&");
			}
		}
		String requestAction = builder.substring(0, builder.length() - 1);
		return get(requestAction, charset);
	}

	/**
	 * 发送get请求 带头部信息
	 * @param url
	 * @param charset
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, String charset, Map<String, String> headers) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		if (ArrayUtils.isNotEmpty(headers)) {
			headers.entrySet().stream().forEach(item -> {
				httpGet.addHeader(item.getKey(), item.getValue());
			});
		}
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse != null)
				return EntityUtils.toString(httpResponse.getEntity(), charset);
		} finally {
			release(httpGet, httpResponse);
		}
		return null;
	}

	public static void main(String[] args) {
		String transNo = "YDH123456";
		String url = "http://blog.csdn.net/jason0539/article/details/44976775";
		String param = "?transNo=" + transNo + "&transNoType=1&lang=zh-cn";

		for (int x = 0; x < 100; x++) {
			int value=x;
			new Thread() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						try {
							String result = HttpClientUtils.get(url + param, "UTF-8");
							logger.info(""+i*value);
						} catch (Exception e) {
							logger.error(e.getMessage(),e);;
							logger.info("philippineXh 获取物流轨迹出错，入参{}" + transNo);
						}
					}
				}
			}.start();
		}

	}

}
