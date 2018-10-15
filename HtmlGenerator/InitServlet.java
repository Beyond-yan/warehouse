package com.foxconn.servlet;

import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.foxconn.servlet.HtmlGenerator;


/**
 * 初始化servlet
 * @author GuoY
 */
public class InitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		String CurrentClassFilePath = this.getClass().getResource("").getPath();
		int lastpath = CurrentClassFilePath.lastIndexOf("classes/");
		String subCurrent = CurrentClassFilePath.substring(0, lastpath);
		final String web_rootPath = subCurrent.replace("WEB-INF/", "index_static.htm");
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				boolean isGenerator = HtmlGenerator.createHtmlPage("http://www.cqjt.gov.cn/index.html", web_rootPath);
				if(isGenerator) {
					System.out.println("生成静态页面成功:" + web_rootPath);
				}else { 
					System.out.println("生成静态页面失败！");
				}
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, (60 * (60 * 1000)));
	}
}
