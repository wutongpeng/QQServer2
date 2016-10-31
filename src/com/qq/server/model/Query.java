package com.qq.server.model;

import java.sql.*;

public class Query {
	
	//查询账户
	public ResultSet queryUser(String[] parameters){		
		String sql = "select nickName, head, lv, content from account where id=? and cipher=?";
		ResultSet re = new ConnectServer().query(sql, parameters);
		return re;
	}
	public ResultSet queryFriendInformation(String parameter){		
		String sql = "select nickName, head, content from account where id=?";
		ResultSet re = new ConnectServer().query(sql, new String[]{parameter});
		return re;
	}
	//查询分组
	public ResultSet queryGroup(String[] parameters){
		String sql = "select friendGroup from friendGroups where genusId=?";
		ResultSet re = new ConnectServer().query(sql, parameters);
		return re;
	}
	//查询好友
	public ResultSet queryFriend(String[] parameters){
		String sql = "select friendId, hasUpdate, remarks from friends where genusId=? and genusGroup=?";
		ResultSet re = new ConnectServer().query(sql, parameters);
		return re;
	}
}
