package com.qq.server.model;

import java.sql.*;

public class ConnectServer {
	
	private String url = "jdbc:mysql://192.168.0.111:3306/warau";
	private String user = "root";
	private String password = "123456";
	
	public int update(String sql, String[] parameters){		
		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			con = DriverManager.getConnection(url,user,password);
			ps = con.prepareStatement(sql);
			count = ps.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(ps != null){
				ps.close();				
			}
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}
	
	public ResultSet query(String sql, String[] parameters){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet re = null;
		try {
			con = DriverManager.getConnection(url,user,password);
			ps = con.prepareStatement(sql);
			for(int i=0; i<parameters.length; i++){
				ps.setString(i+1, parameters[i]);
			}
			re = ps.executeQuery();
			return re;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		try {
			if(ps != null){
				ps.close();				
			}
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return re;
	}
}
