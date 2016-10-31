package sdk.qq.general;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInformation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1142876585817962444L;
	
	//�����˻�
	private final User user;
	//�ǳ�
	private String nickName;
	//����ǩ��
	private String signature;
	//�����б�
	private ArrayList<GroupModel> friendList;
	
	public UserInformation(User user,
			String nickName,
			ArrayList<GroupModel> friendList){
		this.user = user;
		this.nickName = nickName;
		this.friendList = friendList;
	}

	public User getUser() {
		return user;
	}

	public String getNickName() {
		return nickName;
	}

	public String getSignature() {
		return signature;
	}

	public ArrayList<GroupModel> getFriendList() {
		return friendList;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setFriendList(ArrayList<GroupModel> friendList) {
		this.friendList = friendList;
	}	
}
