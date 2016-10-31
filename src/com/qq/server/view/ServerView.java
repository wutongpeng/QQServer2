package com.qq.server.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.qq.server.model.*;
import com.qq.server.tools.CustomizedTitleBorder;

public class ServerView {

	//������
	private JFrame frame;
	
	//ѡ�
	private JTabbedPane tabbedPane;
	
	//�û�����
	private JPanel userManagement;
	
	//��־����
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
		frame = new JFrame("warau������");		
		//ѡ�
		tabbedPane = new JTabbedPane();
		frame.add(tabbedPane);
		setWindowSize();
		frame.setLocationRelativeTo(null);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void createUI(){
		
		//�����û��������
		createOnLineUserManagementPanel();

		createUserManagementPanel();
		
		createLogManagementPanel();
		
		//���÷ָ����ڵı���
		separate.setDividerLocation(0.78);
	}
	
	//�����û��������
	private JPanel onLineUserManagement;
	//���ط�������ť
	private JButton switchServerButton;
	//ǿ�����߰�ť
	private JButton kickButton;
	//�����û���
	private JTable onlineUserTable;
	//���������
	private JTextArea textFiled;
	//����ѡ��
	private JComboBox<String> sendOutOptions;
	//���Ͱ�ť
	private JButton sendOutButton;
	//�ָ�����
	private JSplitPane separate;
	//������
	private QQServer server;
	
	private void createOnLineUserManagementPanel(){

		onLineUserManagement = new JPanel(new BorderLayout());
		tabbedPane.add(onLineUserManagement, "�����û�����");
		
		//ͷ��
		JPanel serverPanel = new JPanel(); 
		serverPanel.setBorder(CustomizedTitleBorder.createTitleBorder("������"));
		onLineUserManagement.add(serverPanel, "North");		
		//������ͼ��
		JLabel serverIcon = new JLabel();
		serverIcon.setPreferredSize(new Dimension(20, 20));
		serverIcon.setIcon(new ImageIcon("Image\\serverIcon.png"));
		serverPanel.add(serverIcon);		
		//���������ذ�ť
		switchServerButton = new JButton("����������");
		switchServerButton.addActionListener(new ActionListener(){
			
			private boolean isOpen = true;
			
			public void actionPerformed(ActionEvent e) {
				
				if(isOpen){
					server = new QQServer();
					server.start();
					switchServerButton.setText("�رշ�����");
					isOpen = false;
				}else{
					server.interrupt();
					switchServerButton.setText("����������");
					isOpen = true;
				}
			}			
		});
		serverPanel.add(switchServerButton);		
		//ǿ�����߰�ť
		kickButton = new JButton("ǿ������");
		serverPanel.add(kickButton);
		
		//�ָ��в����²���ʹ���в����²��ܹ�����
		separate = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		onLineUserManagement.add(separate, "Center");
		
		//�в�
		JPanel onlineUserPanel = new JPanel(new BorderLayout());
		onlineUserPanel.setBorder(CustomizedTitleBorder.createTitleBorder("�����û�"));
		separate.add(onlineUserPanel);
		//�����û���
		onlineUserTable = new JTable(new OnlineUserTableModel());
		onlineUserPanel.add(onlineUserTable);
		
		//�ײ�
		JPanel announcementPanel = new JPanel(new BorderLayout());
		announcementPanel.setBorder(CustomizedTitleBorder.createTitleBorder("ϵͳ����"));
		separate.add(announcementPanel);
		//������
		textFiled = new JTextArea();
		announcementPanel.add(textFiled);
		//�������
		JPanel sendOutPanel = new JPanel(new BorderLayout());
		announcementPanel.add(sendOutPanel, "East");
		//����ѡ��
		String[] options = new String[]{"������",
				"����",
				"Ů��"
		};
		sendOutOptions = new JComboBox<String>(options);
		sendOutPanel.add(sendOutOptions, "North");
		//���Ͱ�ť
		sendOutButton = new JButton("����");
		sendOutButton.setPreferredSize(new Dimension(130, 70));
		sendOutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("�� " + sendOutOptions.getSelectedItem() + " ����:"
						+ textFiled.getText());
			}
		});
		sendOutPanel.add(sendOutButton);
	}
	
	//�û�����
	private void createUserManagementPanel(){

		userManagement = new JPanel();
		tabbedPane.add(userManagement, "�û�����");
	}
	
	//��־����
	private void createLogManagementPanel(){

		logManagement = new JPanel();
		tabbedPane.add(logManagement, "��־����");
	}
	
	//���ô��ڴ�С
	public void setWindowSize(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frame.setSize(screenSize.width-100, screenSize.height-100);
	}
}
