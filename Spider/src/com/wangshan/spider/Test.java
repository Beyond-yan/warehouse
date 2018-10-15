package com.wangshan.spider;


import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 2018 10 11 四
 * @author beyond yan
 * java 截取html文件中的指定内容  a标签 超链接
 */

public class Test {
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\73662\\Desktop\\1.html");
		Long fileLength = file.length();
		byte[] b = new byte[fileLength.intValue()];

		FileInputStream f = new FileInputStream(file);
		f.read(b);
		f.close();
		String content = new String(b, "UTF-8");
		
		int index ;
		int endIndex;
		String regex;
		
		//创建规则 href中不包含 http://www.cqjt.gov.cn/  javascript:* 的a标签
		regex = "<a[^>]*href=\"(?!http://www.cqjt.gov.cn/)(?!javascript:*)(([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
		//创建模板(条件)对象 使用上面定义的规则为参数
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		//使用模板来匹配 从指定内容匹配
		final Matcher ma = pa.matcher(content);
		while (ma.find()) {
			index = ma.group().indexOf("href") + 6;
			endIndex = ma.group().indexOf("\"" , index+1);
			
			
			System.out.println(
					(  ma.group().substring(index, endIndex))
			);
		}
		
	}

}