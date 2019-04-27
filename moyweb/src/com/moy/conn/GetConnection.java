package com.moy.conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class GetConnection {
	public static final String DRIVER = "com.mysql.jdbc.Driver";//定义驱动程序的路径
	public static final String URL = "jdbc:mysql://120.77.203.215:3306/health?useUnicode=true&characterEncoding=utf-8&useSSL=false";//配置数据库连接池
//	public static final String URL = "jdbc:mysql://localhost:3306/health?useUnicode=true&characterEncoding=utf-8&useSSL=false";//配置数据库连接池
	public static final String DATANAME = "root";//数据库用户名
	public static final String PASSWORD  = "123456";//密码
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,DATANAME,PASSWORD);
			System.out.print("Connect DB");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
