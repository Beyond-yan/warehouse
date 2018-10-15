package com.foxconn.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.HtmlUtils;

//public class SpiderTimer implements Job {
@Component("spiderTimer") 
public class SpiderTimer{
	//@Override
	//public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
	@Scheduled(cron = "0 0/30 * * * ?")
	public void execute() throws Exception {
		
        URL url = null;
        URLConnection urlconn = null;	
        BufferedReader br = null;
        PrintWriter pw = null;
       
        
        String regex = "[^\\s]*((<\\s*[aA] target=\"_blank\"\\s+href\\s*=([^>]+\\s*)>))";//url匹配规则
        Pattern p = Pattern.compile(regex);
       
        
        try{
            url = new URL("http://www.mot.gov.cn/difangxinwen/xxlb_fabu/index.html");//爬取的网址、这里爬取的是一个生物网站
            urlconn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(
                    urlconn.getInputStream() , "utf-8"));
            pw = new PrintWriter(new FileWriter("D:/SiteURL.txt"), true);

	        String buf = null;
	        String line = null;
	    	int index ;
			int endIndex;
			int index2;
			int endIndex2 ;
			int flag=-1;
			
			while ((buf = br.readLine()) != null) {
				Matcher buf_m = p.matcher(buf);
				while (buf_m.find()) {
					
					index = buf_m.group().indexOf("title") + 7;
					endIndex = buf_m.group().indexOf("class");
					
					index2 = buf_m.group().indexOf("href") + 6;
					endIndex2 = buf_m.group().indexOf("title");
					
					flag = buf_m.group().indexOf("./" );
					
					//抓出来的数据有两种 判断 做不同处理
					// 1 /zxft2018/cesuogm_gd/index.html
					// 2 ./fbpd_zhejiang/ 
					if(flag == -1){
						pw.println( buf_m.group().substring(index, endIndex) + " amp " +
								("http://www.mot.gov.cn" + buf_m.group().substring(index2, endIndex2))
								);
					} else {
						pw.println( buf_m.group().substring(index, endIndex) + " amp " +
								("http://www.mot.gov.cn/difangxinwen/xxlb_fabu" + buf_m.group().substring(index2 + 1, endIndex2))
								);
					}
				 
				}
			}	
			
			//设置时间输出格式		
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");		
			String time = simpleDateFormat.format(new Date());		
			//打印爬虫执行时间		
			System.out.println("---------爬取新闻数据" + time + "--------------");

        }catch (Exception e){
        	System.out.println("爬取首页新闻失败^_^ 数据为0条记录");
        	
        }
		
	}

}
