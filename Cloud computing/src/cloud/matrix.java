package cloud;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class matrix extends JFrame implements ActionListener{
	JTextArea txtMsg = new JTextArea();//构造一个新的文本区
	JTextField txtSendMsg=new JTextField(20);//构造了一个宽度为 20 个字符的单行文本框 
	JButton btnStop=new JButton("输入完毕");//建立“进入聊天室”按钮
	JButton btnSend1=new JButton("输入A");//建立一个发送按钮
	JButton btnSend2=new JButton("输入B");//建立一个发送按钮
	int n=4;
	String infile1="D:\\A.txt";//创建一个文件保存服务器地址信息
	String infile2="D:\\B.txt";//创建一个文件保存服务器地址信息
    void infile11(String data){//写文件保存矩阵A

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile1,true));
    		out.write(data);//写入一行矩阵值
    		out.newLine();//换行
    		out.close();//关闭文件
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    void infile22(String data){//写文件保存矩阵B

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile2,true));
    		out.write(data);//写入一行矩阵值
    		out.newLine();//换行
    		out.close();//关闭文件
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	public matrix(){
		Container con =this.getContentPane();//初始化一个容器
		txtMsg.setEditable(true);//允许编辑文本区
		btnSend1.setEnabled(true);//允许启用“输入”按钮
		btnSend2.setEnabled(true);//允许启用“输入”按钮
		btnStop.setEnabled(true);//允许启用“禁止输入”按钮
		txtSendMsg.setEditable(true);//允许编辑文本框
		/*利用JPanel容器管理界面*/
		JPanel p1=new JPanel();
		JScrollPane p2=new JScrollPane(txtMsg);//滚动面板
		JPanel p3=new JPanel();
		p1.add(btnStop);
		p3.add(txtSendMsg);
		p3.add(btnSend1);
		p3.add(btnSend2);
		con.add(p1, "North");
		con.add(p2, "Center");
		con.add(p3, "South");
		/*监听*/
		txtSendMsg.addActionListener(this);
		btnSend1.addActionListener(this);
		btnSend2.addActionListener(this);
		btnStop.addActionListener(this);
		setSize(400,400);
		setTitle("矩阵值");
		setVisible(true);//显示窗口
		txtMsg.append("请输入矩阵A和矩阵B的值\n"+"A,B均为"+n+"阶矩阵\n");
	}
	public void actionPerformed(ActionEvent e)//事件触发器，单击鼠标触发
	{
		if(e.getSource()==btnSend1 ||e.getSource()==txtSendMsg){//输入矩阵A，并重置文本框
			infile11(txtSendMsg.getText());
			txtMsg.append(txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}
		else if(e.getSource()==btnSend2 ||e.getSource()==txtSendMsg){//输入矩阵B，并重置文本框
			infile22(txtSendMsg.getText());
			txtMsg.append(txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}	
		else if(e.getSource()==btnStop){
			dispose();//关闭窗口
//			new client();
		}
	}
	public static void main(String args[]){
//		new matrix();
	}
	
}
