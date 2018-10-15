package com.wangshan.spider;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
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

public class TestHtmlUnit {
	public static void main(String[] args) throws Exception {
		WebClient web = new WebClient(BrowserVersion.CHROME);
		web.getOptions().setJavaScriptEnabled(true);
		web.getOptions().setCssEnabled(false);
		web.setAjaxController(new NicelyResynchronizingAjaxController());
		web.getOptions().setThrowExceptionOnScriptError(false);//��JSִ�г����ʱ���Ƿ��׳��쳣, ����ѡ����Ҫ
		HtmlPage page = web.getPage("http://www.cqjt.gov.cn/");
		
		web.waitForBackgroundJavaScript(30000);//�첽JSִ����Ҫ��ʱ,���������߳�Ҫ����30��,�ȴ��첽JSִ�н���
		
		System.out.println(page.asXml());



	}
}

/*
	��Ҫ����HtmlUnit jar ��֧�� ��Ҫjdk1.8 1.7̫��
	
	�����ͨ��ִ���첽JS���󣬽���ȡ���Ķ�̬���ݣ���Ⱦ�������documentҳ���У������ձ�������ǿ�������ҳ���������ⲿ����Ҫִ��JS�����ȡ�����ݣ�httpClient���Ե�����Ϊ����
	��ͨ��ͨ��urlץȡ��ҳ�ķ�ʽ������ url.openConnection
	 
	HtmlUnit������ôһ������⣬���������˽���չʾ�������е��첽����������û����չʾ��һ���ʱ�Ĺ�����HtmlUnit�������һ����������ҳҪ��ʵ�ʵ����������ˡ����Ҹ��ݲ�ͬ����
	HtmlUnit����ģ�������ϳ��õ��������Chrome��Firefox��IE������ȡ�

*/