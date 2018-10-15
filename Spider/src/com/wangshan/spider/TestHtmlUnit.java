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
 * 2018 10 11 四
 * @author beyond yan
 * java 截取html文件中的指定内容  a标签 超链接
 */

public class TestHtmlUnit {
	public static void main(String[] args) throws Exception {
		WebClient web = new WebClient(BrowserVersion.CHROME);
		web.getOptions().setJavaScriptEnabled(true);
		web.getOptions().setCssEnabled(false);
		web.setAjaxController(new NicelyResynchronizingAjaxController());
		web.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
		HtmlPage page = web.getPage("http://www.cqjt.gov.cn/");
		
		web.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
		
		System.out.println(page.asXml());



	}
}

/*
	需要加入HtmlUnit jar 的支持 需要jdk1.8 1.7太低
	
	浏览器通过执行异步JS请求，将获取到的动态数据，渲染到最初的document页面中，才最终变成了我们看到的网页。而对于这部分需要执行JS代码获取的数据，httpClient就显得无能为力了
	普通的通过url抓取网页的方式都不行 url.openConnection
	 
	HtmlUnit就是这么一个程序库，用来做出了界面展示以外所有的异步工作。由于没有了展示这一块耗时的工作，HtmlUnit加载完成一个完整的网页要比实际的浏览器块多了。并且根据不同配置
	HtmlUnit可以模拟市面上常用的浏览器如Chrome、Firefox、IE浏览器等。

*/