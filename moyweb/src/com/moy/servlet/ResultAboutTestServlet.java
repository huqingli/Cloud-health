package com.moy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.moy.impl.HeartAgeExplainPOJOImpl;
import com.moy.impl.ResultPOJOImpl;
import com.moy.impl.UserPOJOImpl;
import com.moy.pojo.ResultAnalysisPOJO;
import com.moy.pojo.UserPOJO;

public class ResultAboutTestServlet extends HttpServlet {

	
	public ResultAboutTestServlet() {
		super();
	}

	
	public void destroy() {
		super.destroy(); 
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResultAnalysisPOJO resultanalysis=null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userName = request.getParameter("userName");
		String age = request.getParameter("age");
		String UID = request.getParameter("UID");
		String userid = ResultPOJOImpl.getUserid(userName);
		String heartrate = ResultPOJOImpl.getheartrate(userid);
		System.out.print("rate:");
		System.out.println(heartrate);
		System.out.println(UID);
		if(heartrate == null){               // 判断是否进行过心率测量
			JSONObject json = new JSONObject();
			json.put("message","fail");
			System.out.println("rate = 0");
			PrintWriter out = response.getWriter(); 
	        out.print(json.toString());  
	        out.flush();  
	        out.close(); 
	        return;
		}
		if(UID.equals("Test")){
			resultanalysis = ResultPOJOImpl.createweb(userName, heartrate, age) ;
		}
		if(UID.equals("Sport")){
			resultanalysis = ResultPOJOImpl.createwebOfSport(userName, heartrate, age) ;
		}
		if(UID.equals("Food")){
			resultanalysis = ResultPOJOImpl.createwebOfFood(userName, heartrate, age) ;
		}
		JSONObject json = new JSONObject();
		if(resultanalysis==null)
			 json.put("message","fail");
		else
		{
			System.out.print("foodcomment:");
			System.out.println(resultanalysis.getFoodcomment());
			 json.put("message","success");
        	 json.put("id", resultanalysis.getId());
        	 json.put("grade", resultanalysis.getGrade());
        	 json.put("resultcomment", resultanalysis.getResultcomment());
        	 json.put("sportcomment", resultanalysis.getSportcomment());
        	 json.put("foodcomment", resultanalysis.getFoodcomment());
        	 json.put("heartage", resultanalysis.getHeartage());
        	 json.put("heartcomment", resultanalysis.getHeartcomment());
        	 json.put("food", resultanalysis.getFood());
        	 json.put("sport", resultanalysis.getSport());
        	 json.put("rate", resultanalysis.getRate());
		}
		PrintWriter out = response.getWriter(); 
        out.print(json.toString());  
        out.flush();  
        out.close(); 
		/*
		request.setAttribute("list", resultanalysis);  //绑定到JSP页面
	    request.getRequestDispatcher("index.jsp").forward(request, response); 
          */
        
	}

	
	public void init() throws ServletException {
		// Put your code here
	}

}
