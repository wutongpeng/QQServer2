package sdk.qq.general.parcel;

import java.io.Serializable;

import sdk.qq.general.User;

public class UserVerificationParcel  implements ParcelModel, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1956551231810567245L;

	//��Ϣ����
	private final MessageEnum message = MessageEnum.USER_VERIFICATION;
	//�˻�
	private final User user;
	
	public UserVerificationParcel(User user){
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}
	
	public MessageEnum getMessage(){
		return message;
	}
}
