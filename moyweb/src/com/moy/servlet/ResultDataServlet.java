package com.moy.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.moy.impl.ResultPOJOImpl;
import com.moy.impl.UserPOJOImpl;
import com.moy.pojo.ResultPOJO;
import com.moy.pojo.UserPOJO;
public class ResultDataServlet extends HttpServlet{
	/**
	 * Constructor of the object.
	 */
	public ResultDataServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		ResultPOJO results = null;
		Boolean flag = false;
		int userid = Integer.parseInt((String)request.getParameter("userid"));
		JSONObject json = new JSONObject();
		results = UserPOJOImpl.loginResultData(userid);
		if (results == null) {
			json.put("message", "fail");
		} else {
			json.put("message", "success");
			json.put("userId", results.getUserId());
			if(results.gethighpressure()!=null)
				json.put("highP", results.gethighpressure());
			else
				json.put("highP", "");
			if(results.getlowpressure()!=null)
				json.put("lowP", results.getlowpressure());
			else
				json.put("lowP", "");
			if(results.getHeartrate()!=null)
				json.put("heartrate", results.getHeartrate());
			else
				json.put("heartrate", "");
			if(results.getHeartage()!=null)
				json.put("heartage", results.getHeartage());
			else
				json.put("heartage", "");
			if(results.getRespirationrate()!=null)
				json.put("respirationrate", results.getRespirationrate());
			else
				json.put("respirationrate", "");
			if(results.getRelaxationlevel()!=null)
				json.put("relaxationlevel", results.getRelaxationlevel());
			else
				json.put("relaxationlevel", "");
			if(results.getFmheartrate()!=null)
				json.put("fmheartrate", results.getFmheartrate());
			else
				json.put("fmheartrate", "null");
			if(results.getRrinterval()!=null)
				json.put("rrinterval", results.getRrinterval());
			else
				json.put("rrinterval", "");
		}
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
