package com.qq.server.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.qq.server.model.*;
import com.qq.server.tools.CustomizedTitleBorder;

public class ServerView {

	//主窗体
	private JFrame frame;
	
	//选项卡
	private JTabbedPane tabbedPane;
	
	//用户管理
	private JPanel userManagement;
	
	//日志管理
	private JPanel logManagement;
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {

			e1.printStackTrace();
		} 
		new ServerView().createUI();
	}
	
	public ServerView(){		
		frame = new JFrame("warau服务器");		
		//选项卡
		tabbedPane = new JTabbedPane();
		frame.add(tabbedPane);
		setWindowSize();
		frame.setLocationRelativeTo(null);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void createUI(){
		
		//在线用户管理面板
		createOnLineUserManagementPanel();

		createUserManagementPanel();
		
		createLogManagementPanel();
		
		//设置分隔窗口的比例
		separate.setDividerLocation(0.78);
	}
	
	//在线用户管理面板
	private JPanel onLineUserManagement;
	//开关服务器按钮
	private JButton switchServerButton;
	//强制下线按钮
	private JButton kickButton;
	//在线用户表
	private JTable onlineUserTable;
	//公告输入框
	private JTextArea textFiled;
	//发送选项
	private JComboBox<String> sendOutOptions;
	//发送按钮
	private JButton sendOutButton;
	//分隔窗口
	private JSplitPane separate;
	//服务器
	private QQServer server;
	
	private void createOnLineUserManagementPanel(){

		onLineUserManagement = new JPanel(new BorderLayout());
		tabbedPane.add(onLineUserManagement, "在线用户管理");
		
		//头部
		JPanel serverPanel = new JPanel(); 
		serverPanel.setBorder(CustomizedTitleBorder.createTitleBorder("服务器"));
		onLineUserManagement.add(serverPanel, "North");		
		//服务器图标
		JLabel serverIcon = new JLabel();
		serverIcon.setPreferredSize(new Dimension(20, 20));
		serverIcon.setIcon(new ImageIcon("Image\\serverIcon.png"));
		serverPanel.add(serverIcon);		
		//服务器开关按钮
		switchServerButton = new JButton("启动服务器");
		switchServerButton.addActionListener(new ActionListener(){
			
			private boolean isOpen = true;
			
			public void actionPerformed(ActionEvent e) {
				
				if(isOpen){
					server = new QQServer();
					server.start();
					switchServerButton.setText("关闭服务器");
					isOpen = false;
				}else{
					server.interrupt();
					switchServerButton.setText("启动服务器");
					isOpen = true;
				}
			}			
		});
		serverPanel.add(switchServerButton);		
		//强制下线按钮
		kickButton = new JButton("强制下线");
		serverPanel.add(kickButton);
		
		//分隔中部和下部，使得中部和下部能够拉伸
		separate = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		onLineUserManagement.add(separate, "Center");
		
		//中部
		JPanel onlineUserPanel = new JPanel(new BorderLayout());
		onlineUserPanel.setBorder(CustomizedTitleBorder.createTitleBorder("在线用户"));
		separate.add(onlineUserPanel);
		//在线用户表
		onlineUserTable = new JTable(new OnlineUserTableModel());
		onlineUserPanel.add(onlineUserTable);
		
		//底部
		JPanel announcementPanel = new JPanel(new BorderLayout());
		announcementPanel.setBorder(CustomizedTitleBorder.createTitleBorder("系统公告"));
		separate.add(announcementPanel);
		//输入栏
		textFiled = new JTextArea();
		announcementPanel.add(textFiled);
		//发送面板
		JPanel sendOutPanel = new JPanel(new BorderLayout());
		announcementPanel.add(sendOutPanel, "East");
		//发送选项
		String[] options = new String[]{"所有人",
				"男性",
				"女性"
		};
		sendOutOptions = new JComboBox<String>(options);
		sendOutPanel.add(sendOutOptions, "North");
		//发送按钮
		sendOutButton = new JButton("发送");
		sendOutButton.setPreferredSize(new Dimension(130, 70));
		sendOutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("对 " + sendOutOptions.getSelectedItem() + " 发送:"
						+ textFiled.getText());
			}
		});
		sendOutPanel.add(sendOutButton);
	}
	
	//用户管理
	private void createUserManagementPanel(){

		userManagement = new JPanel();
		tabbedPane.add(userManagement, "用户管理");
	}
	
	//日志管理
	private void createLogManagementPanel(){

		logManagement = new JPanel();
		tabbedPane.add(logManagement, "日志管理");
	}
	
	//设置窗口大小
	public void setWindowSize(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frame.setSize(screenSize.width-100, screenSize.height-100);
	}
}
