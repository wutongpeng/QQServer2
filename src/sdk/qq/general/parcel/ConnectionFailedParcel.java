package sdk.qq.general.parcel;

import java.io.Serializable;


//���Ӧ���Ƕ����
public class ConnectionFailedParcel implements ParcelModel, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -998253461261267572L;
	
	private final MessageEnum message = MessageEnum.CONNECTION_FAILED;
	
	public MessageEnum getMessage() {
		return message;
	}
}
