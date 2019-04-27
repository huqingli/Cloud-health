package com.moy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moy.impl.UserPOJOImpl;
import com.moy.pojo.ResultPOJO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetdataServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetdataServlet() {
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
		
		String userName = request.getParameter("userName");
		
		System.out.println("22ww");
		
		JSONArray array = new JSONArray();
		List<ResultPOJO> list = UserPOJOImpl.getdata(userName);
		
		for(ResultPOJO result:list){
			JSONObject json = new JSONObject();
			
			json.put("userId",result.getUserId());
			json.put("userName",result.getUserName());
			json.put("rate",result.getRate());

			
			array.add(json);
		}
		PrintWriter out = response.getWriter();
		out.print(array.toString());
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
