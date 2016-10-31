package com.qq.server.model;

import java.io.*;
import java.net.*;

import sdk.qq.general.parcel.*;


//与客户端互动的线程
public class ClientSocketThread extends Thread{
	
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	//识别
	private String NO;
	//连接失败计数
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
							//给客户端回复对方没有登录的消息包
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
		//无论因为什么情况而退出循环时都关闭资源
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
			//个人猜测该异常是因为包裹流在使用的原始流先关闭了的关系导致的
			//所以只要顺序正确应该不会出现该异常
			e1.printStackTrace();
		}
	}
	
	//返回套接字
	public Socket getSocket(){
		return socket;
	}
}
