package com.moy.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploaderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploaderServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//首先判断一下 上传的数据是表单数据还是一个带文件的数据 
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {   //如果为true 说明是一个带有文件的数据
			//拿到servlet的真实路径 
			String realpath = request.getSession().getServletContext().getRealPath("/files");
			//打印一下路径
			System.out.println("realpath-"+realpath);
			File dir = new File("D:/image");
			if (!dir.exists())
				dir.mkdirs(); //如果目录不存在 把这个目录给创建出来 
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory); //获取到上传文件的对象upload
			upload.setHeaderEncoding("UTF-8");
			try {
				//判断一下上传的数据类型
				List<FileItem> items = upload.parseRequest(request);
				String value="untitled";
				for (FileItem item : items) {
					if (item.isFormField()) { //上传的数据类型 是一个表单类型
						String name1 = item.getFieldName();// 得到请求参数的名称
						value = item.getString("UTF-8");// 得到参数值
						System.out.println(name1 + "=" + value);
					} else {
						//说明是一个文件类型   进行上传
						item.write(new File(dir, value
								+ item.getName().substring(item.getName().lastIndexOf("."))));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
