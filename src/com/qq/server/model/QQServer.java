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

	//服务器
	ServerSocket server = null;
	Socket socket = null;
	ObjectInputStream objectInputStream = null;
	ObjectOutputStream objectOutputStream = null;
	//当前程序所在路径
	String route;
	{
		route = System.getProperty("user.dir") + "/";
	}
	
	@Override
	public void run() {
		try {			
			server = new ServerSocket(18010);
			System.out.println("启动了");
			while(true){				
				socket = server.accept();
				System.out.println("有人连接了");
				objectInputStream = new ObjectInputStream(socket.getInputStream());	
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				try {
					ParcelModel message = (ParcelModel)objectInputStream.readObject();
					switch(message.getMessage()){
					case USER_VERIFICATION:	
						ParcelModel reply = null;						
						User user = ((UserVerificationParcel)message).getUser();
						//判断账号是否已登录
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
					//***将来这里要做一个回复
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	//验证用户
	public UserInformation verificationUser(User user){
		System.out.println("验证");
		//取得账户信息
		String NO = user.toString();
		//创建负责查询的对象
		Query query = new Query();
		ResultSet resultSet1 = query.queryUser(new String[]{NO, user.getPassword()});	
		//如果该用户合法
		if(resultSet1 != null){
			//继续查询该用户的分组
			ResultSet resultSet2 = query.queryGroup(new String[]{NO});
			ArrayList<GroupModel> groupModels = new ArrayList<GroupModel>();;
			try {
				//resultSet2为空貌似也不会出错的样子
				while(resultSet2.next()){
					//取得分组名并构建
					String groupName = resultSet2.getString("friendGroup");
					GroupModel group = new GroupModel(groupName);
					//继续查询该分组下的好友
					ResultSet resultSet3 = query.queryFriend(new String[]{NO, groupName});
					while(resultSet3.next()){
						//需要继续查询该好友的一些账户信息
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
	
	//将图像转换为字节数组好用于传输
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
	
	//关闭资源
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}