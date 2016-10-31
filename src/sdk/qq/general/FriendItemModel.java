package sdk.qq.general;

import java.io.Serializable;

public class FriendItemModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1129594901962565953L;
	
	private byte[] head;
	private String remarks;
	private String nickName;
	private boolean newMessage;
	private String Signature;
	private String NO;
	
	public FriendItemModel(byte[] head,
			String remarks,
			String nickName,
			boolean newMessage,
			String Signature,
			String NO){
		
		this.head = head;
		this.remarks = remarks;
		this.nickName = nickName;
		this.newMessage = newMessage;
		this.Signature = Signature;
		this.NO = NO;
	}

	public byte[] getHead() {
		return head;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getNickName() {
		return nickName;
	}

	public boolean isNewMessage() {
		return newMessage;
	}

	public String getSignature() {
		return Signature;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setNewMessage(boolean newMessage) {
		this.newMessage = newMessage;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public String getNO() {
		return NO;
	}

	public void setNO(String nO) {
		NO = nO;
	}
}
