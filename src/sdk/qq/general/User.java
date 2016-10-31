package sdk.qq.general;

import java.io.Serializable;

/*
 * ����һ���˻�
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -84845435813766646L;
	
	private String user;
	private String password;
	private String headURL;
	private StateEnum state;
	private boolean rememberPassWord = false;
	private boolean automaticLogin = false;
	
	public User(String user, 
			String headURL, 
			StateEnum state, 
			boolean rememberPassWord, 
			boolean automaticLogin) {
		
		this.user = user;
		this.headURL = headURL;
		this.state = state;
		this.rememberPassWord = rememberPassWord;
		this.automaticLogin = automaticLogin;
	}

	public String getHeadURL() {
		return headURL;
	}

	public void setHeadURL(String headURL) {
		this.headURL = headURL;
	}

	public StateEnum getState() {
		return state;
	}

	public void setState(StateEnum state) {
		this.state = state;
	}

	public boolean isRememberPassWord() {
		return rememberPassWord;
	}

	public void setRememberPassWord(boolean rememberPassWord) {
		this.rememberPassWord = rememberPassWord;
	}

	public boolean isAutomaticLogin() {
		return automaticLogin;
	}

	public void setAutomaticLogin(boolean automaticLogin) {
		this.automaticLogin = automaticLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString(){
		return user;
	}
}
