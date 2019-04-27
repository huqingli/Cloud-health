package com.moy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moy.impl.ResultPOJOImpl;
import com.moy.impl.UserPOJOImpl;

public class ResultServlet extends HttpServlet {

	
	/**
	 * Constructor of the object.
	 */
	public ResultServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String state = request.getParameter("state");
		if (state.equals("heart")) {
			String heartrate = request.getParameter("heartrate");
			String heartage = request.getParameter("heartage");
			String relaxationlevel = request.getParameter("relaxationlevel");
			String respirationrate = request.getParameter("respirationrate");
			String fmheartrate = request.getParameter("fmheartrate");
			String date = request.getParameter("date");
			String rrinterval = request.getParameter("rrinterval");
			int userid = Integer.parseInt((String)request.getAttribute("userid"));
			boolean b = ResultPOJOImpl.addresult(heartrate, heartage, relaxationlevel, respirationrate,
					fmheartrate,date,rrinterval,userid);
		}
		if (state.equals("bldpressure")) {
			String highpressure = request.getParameter("highpressure");
			String lowpressure = request.getParameter("lowpressure");
			int userid = Integer.parseInt((String)request.getAttribute("userid"));
			boolean b = ResultPOJOImpl.addresult1(highpressure,lowpressure,userid);
		}
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
