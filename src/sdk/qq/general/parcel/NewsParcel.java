package sdk.qq.general.parcel;

import java.io.Serializable;

//��������ͻ��˴�����Ϣ�İ�
public class NewsParcel implements ParcelModel, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2658530149257278585L;
	
	//��Ϣ����
	private final MessageEnum message = MessageEnum.NEWS;
	//���ͷ�
	private final String sender;
	//���ͷ���Ϣ
	private final String senderInformation;
	//���շ�
	private final String recipient;
	//����
	private final Object content;
	
	public NewsParcel(String sender, String senderInformation, String recipient, Object content){
		this.sender = sender;
		this.senderInformation =senderInformation;
		this.recipient = recipient;
		this.content = content;
	}
	
	public MessageEnum getMessage(){
		return message;
	}

	public String getSender() {
		return sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public Object getContent() {
		return content;
	}

	public String getSenderInformation() {
		return senderInformation;
	}
}
