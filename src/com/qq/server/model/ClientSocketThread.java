package com.qq.server.model;

import java.io.*;
import java.net.*;

import sdk.qq.general.parcel.*;


//��ͻ��˻������߳�
public class ClientSocketThread extends Thread{
	
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	//ʶ��
	private String NO;
	//����ʧ�ܼ���
	private int num=0;
	
	public ClientSocketThread(String NO, Socket socket){
		this.NO = NO;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		while(num<10){
			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				try {
					ParcelModel parcel = (ParcelModel) objectInputStream.readObject();
					switch(parcel.getMessage()){
					case NEWS:
						NewsParcel newsParcel = (NewsParcel) parcel;
						String key = newsParcel.getRecipient();
						ClientSocketThread clientSocketThread = (ClientSocketThread) ManagementUser.get(key);
						if(clientSocketThread==null){
							//���ͻ��˻ظ��Է�û�е�¼����Ϣ��
							break;
						}
						Socket clientSocket = clientSocketThread.getSocket();
						objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
						objectOutputStream.writeObject(parcel);						
						break;
					default:
						break;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					num++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				num++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		//������Ϊʲô������˳�ѭ��ʱ���ر���Դ
		try {
			ManagementUser.remove(NO);
			if(objectOutputStream != null){
				objectOutputStream.close();					
			}
			if(objectInputStream != null){
				objectInputStream.close();
			}
			if(socket != null){
				socket.close();
			}
		} catch (IOException e1) {
			//���˲²���쳣����Ϊ��������ʹ�õ�ԭʼ���ȹر��˵Ĺ�ϵ���µ�
			//����ֻҪ˳����ȷӦ�ò�����ָ��쳣
			e1.printStackTrace();
		}
	}
	
	//�����׽���
	public Socket getSocket(){
		return socket;
	}
}
