package com.qq.server.model;

import java.util.HashMap;

public class ManagementUser {

	private static HashMap<String, ClientSocketThread> preservation = new HashMap<String, ClientSocketThread>();
	
	public ManagementUser(){
		
	}	
	public static <K, V> void put(String key, ClientSocketThread value){
		preservation.put(key, value);
	}
	
	public static ClientSocketThread get(String key){		
		return preservation.get(key);
	}
	
	public static void remove(String key){
		preservation.remove(key);
	}
	
	public static boolean containsKey(String key){
		return preservation.containsKey(key);
	}
	 
}
