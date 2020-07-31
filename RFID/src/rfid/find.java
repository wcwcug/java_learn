package rfid;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
public class find extends JFrame implements ActionListener{//查询学生考勤信息
	JTextArea txtMsg = new JTextArea();//构造一个新的文本区
	JTextField txtSendMsg=new JTextField(20);//构造了一个宽度为 20 个字符的单行文本框 
	JButton btnStop=new JButton("退出查询");//建立一个退出界面按钮
	JButton btnSend=new JButton("输入学生学号");//建立“输入”按钮
	int flag=0;

	void link(String num){//数据库建立链接
		System.out.println("加载驱动成功！");
		 String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		  String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=Test";
		  String userName="sa";
		  String userPwd="wangchenwei";
        try
        {
        	Class.forName(driverName);
        	System.out.println("加载驱动成功！");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println("加载驱动失败！");
        }
        try{
    	Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);//创建连接数据库对象
		System.out.println("连接数据库成功！");
		Statement st = dbConn.createStatement();//创建SQL命令对象
		ResultSet rs=st.executeQuery("SELECT * FROM s"+num);//遍历查找学生信息表
		System.out.println("学生考勤信息如下：");
		System.out.println("学号"+"\t"+"姓名"+"\t"+"时间");
		while(rs.next())
		{
			System.out.println(rs.getString("num")+"\t"+rs.getString("name")+"\t"+rs.getString("time"));
		}
		st.close();
		dbConn.close();
		}catch(Exception e){
        	e.printStackTrace();
        	System.out.println("SQL Server连接失败！");
        }
		
	}
	public find(){
		Container con =this.getContentPane();//初始化一个容器
		txtMsg.setEditable(true);//允许编辑文本区
		btnSend.setEnabled(true);//允许启用“输入学生学号”按钮
		btnStop.setEnabled(true);//允许启用“退出查询”按钮
		txtSendMsg.setEditable(true);//允许编辑文本框
		/*利用JPanel容器管理界面*/
		JPanel p1=new JPanel();
		JScrollPane p2=new JScrollPane(txtMsg);//滚动面板
		JPanel p3=new JPanel();
		p1.add(btnStop);
		p3.add(txtSendMsg);
		p3.add(btnSend);
		con.add(p1, "North");
		con.add(p2, "Center");
		con.add(p3, "South");
		/*监听*/
		txtSendMsg.addActionListener(this);
		btnSend.addActionListener(this);
		btnStop.addActionListener(this);
		setSize(400,400);
		setTitle("学生考勤信息查询");
		setVisible(true);//显示窗口
	}
	public void actionPerformed(ActionEvent e)//事件触发器，单击鼠标触发
	{
		if(e.getSource()==btnSend ||e.getSource()==txtSendMsg){//输入学生学号
			link(txtSendMsg.getText());//建立数据库连接并查询信息
			txtMsg.append("学生学号为："+txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}else if(e.getSource()==btnStop){
			dispose();//关闭窗口
		}
	}
	public static void main(String args[]){
		new find();
	}
}

