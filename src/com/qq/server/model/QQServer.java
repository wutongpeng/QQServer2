package com.qq.server.model;

import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import sdk.qq.general.*;
import sdk.qq.general.parcel.*;

public class QQServer extends Thread{

	//������
	ServerSocket server = null;
	Socket socket = null;
	ObjectInputStream objectInputStream = null;
	ObjectOutputStream objectOutputStream = null;
	//��ǰ��������·��
	String route;
	{
		route = System.getProperty("user.dir") + "/";
	}
	
	@Override
	public void run() {
		try {			
			server = new ServerSocket(18010);
			System.out.println("������");
			while(true){				
				socket = server.accept();
				System.out.println("����������");
				objectInputStream = new ObjectInputStream(socket.getInputStream());	
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				try {
					ParcelModel message = (ParcelModel)objectInputStream.readObject();
					switch(message.getMessage()){
					case USER_VERIFICATION:	
						ParcelModel reply = null;						
						User user = ((UserVerificationParcel)message).getUser();
						//�ж��˺��Ƿ��ѵ�¼
						if(ManagementUser.containsKey(user.toString())){
							reply = new LoggedInParcel();
							objectOutputStream.writeObject(reply);
							socket.close();
							break;
						}
						UserInformation userInformation = verificationUser(user);
						if(userInformation!=null){
							reply = new UserThroughParcel(userInformation);
							objectOutputStream.writeObject(reply);
							ClientSocketThread clientSocketThread = new ClientSocketThread(user.toString(), socket);
							clientSocketThread.start();
							ManagementUser.put(user.toString(), clientSocketThread);
						}else{
							reply = new NotThroughParcel();
							objectOutputStream.writeObject(reply);
							socket.close();
						}
						break;
					default:
						break;					
					}
					
				} catch (ClassNotFoundException e) {
					//***��������Ҫ��һ���ظ�
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	//��֤�û�
	public UserInformation verificationUser(User user){
		System.out.println("��֤");
		//ȡ���˻���Ϣ
		String NO = user.toString();
		//���������ѯ�Ķ���
		Query query = new Query();
		ResultSet resultSet1 = query.queryUser(new String[]{NO, user.getPassword()});	
		//������û��Ϸ�
		if(resultSet1 != null){
			//������ѯ���û��ķ���
			ResultSet resultSet2 = query.queryGroup(new String[]{NO});
			ArrayList<GroupModel> groupModels = new ArrayList<GroupModel>();;
			try {
				//resultSet2Ϊ��ò��Ҳ������������
				while(resultSet2.next()){
					//ȡ�÷�����������
					String groupName = resultSet2.getString("friendGroup");
					GroupModel group = new GroupModel(groupName);
					//������ѯ�÷����µĺ���
					ResultSet resultSet3 = query.queryFriend(new String[]{NO, groupName});
					while(resultSet3.next()){
						//��Ҫ������ѯ�ú��ѵ�һЩ�˻���Ϣ
						String friendId = resultSet3.getString("friendId");
						ResultSet resultSet4 = query.queryFriendInformation(friendId);
						while(resultSet4.next()){
							String headFile = route + resultSet4.getString("head");
							byte[] head = createByte(new File(headFile));
							boolean hasUpdate = false;
							switch(resultSet3.getInt("hasUpdate")){
							case 0:
								hasUpdate = false;
							case 1:
								hasUpdate = true;
							}
							FriendItemModel friend = new FriendItemModel(head,
									resultSet3.getString("remarks"),
									resultSet4.getString("nickName"),
									hasUpdate,
									resultSet4.getString("content"),
									friendId);
							group.add(friend);
						}
					}
					groupModels.add(group);
				}
				resultSet1.next();									
				UserInformation userInformation = new UserInformation(user,
						resultSet1.getString("nickName"),
						groupModels
						);
				return userInformation;
			} catch (SQLException e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	//��ͼ��ת��Ϊ�ֽ���������ڴ���
	public byte[] createByte(File s){
		BufferedImage bu;
		try {
			  bu = ImageIO.read(s);
			  ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
			  try {
			   ImageIO.write(bu, "png", imageStream);
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
			  return imageStream.toByteArray();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	//�ر���Դ
	public void close(){
		try {
			if(objectInputStream != null){
				
				if(objectOutputStream != null){
					objectOutputStream.close();
				}
				objectInputStream.close();
				socket.close();
				server.close();
			}
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}