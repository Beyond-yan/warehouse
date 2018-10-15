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
 * 2018 10 11 ��
 * @author beyond yan
 * java ��ȡhtml�ļ��е�ָ������  a��ǩ ������
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
		
		//�������� href�в����� http://www.cqjt.gov.cn/  javascript:* ��a��ǩ
		regex = "<a[^>]*href=\"(?!http://www.cqjt.gov.cn/)(?!javascript:*)(([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
		//����ģ��(����)���� ʹ�����涨��Ĺ���Ϊ����
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		//ʹ��ģ����ƥ�� ��ָ������ƥ��
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