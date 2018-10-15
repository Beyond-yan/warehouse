package com.foxconn.servlet;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class HtmlGenerator {

	/**
	 * 根据模版及参数产生静态页面
	 * @param url
	 * @param htmlFileName
	 * @return
	 */
	public static boolean createHtmlPage(String url, String htmlFileName) {
		boolean status = false;
		int statusCode = 0;
		// HttpClient实例
		HttpClient httpClient = null;
		// GetMethod实例
		GetMethod getMethod = null; 
		String page = null;
		String webappname = null;

		BufferedReader br = null;
		InputStream in = null;
		StringBuffer sb = null;
		String line = null;
		Logger logger = Logger.getLogger(HtmlGenerator.class);
		try {
			// 创建一个HttpClient实例充当模拟浏览器
			httpClient = new HttpClient();
			// 设置httpclient读取内容时使用的字符集
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			// 创建GET方法的实例
			getMethod = new GetMethod(url);
			// 使用系统提供的默认的恢复策略，在发生异常时候将自动重试3次
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			// 设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
			getMethod.addRequestHeader("Content-Type", "text/html;charset=UTF-8");
			// 执行Get方法并取得返回状态码，200表示正常，其它代码为异常
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != 200) {
				logger.fatal("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName + "时出错!");
			} else {
				// 读取解析结果
				sb = new StringBuffer();
				in = getMethod.getResponseBodyAsStream();
				br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				if (br != null)
					br.close();
				page = sb.toString();
				// 将页面中的相对路径替换成绝对路径，以确保页面资源正常访问
				page = page.replaceAll("\\.\\./\\.\\./\\.\\./", webappname + "/");
				page = page.replaceAll("\\.\\./\\.\\./", webappname + "/");
				page = page.replaceAll("\\.\\./", webappname + "/");
				// 将解析结果写入指定的静态HTML文件中，实现静态HTML生成
				writeHtml(htmlFileName, page);
				status = true;
			}
		} catch (Exception ex) {
			logger.fatal("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName + "时出错:" + ex.getMessage());
		} finally {
			// 释放http连接
			getMethod.releaseConnection();
		}
		return status;
	}

	// 将解析结果写入指定的静态HTML文件中
	private synchronized static void writeHtml(String htmlFileName, String page) throws Exception {
		
		OutputStreamWriter fw1 = new OutputStreamWriter(new FileOutputStream(htmlFileName), "UTF-8");
		fw1.write(page);
		if (fw1 != null) {
			fw1.close();
		}
	}

}
