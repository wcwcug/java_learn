package cloud;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
public class serverAddress extends JFrame implements ActionListener{//输入服务器端信息
	JTextArea txtMsg = new JTextArea();//构造一个新的文本区
	JTextField txtSendMsg=new JTextField(20);//构造了一个宽度为 20 个字符的单行文本框 
	JButton btnStop=new JButton("输入完毕");//建立“进入聊天室”按钮
	JButton btnSend=new JButton("输入");//建立一个发送按钮
	int flag=0;
	String infile1="D:\\node.txt";//创建一个文件保存服务器地址信息
    void infile(String data){//写文件保存服务器地址信息

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile1,true));
    		out.write(data);//写入服务器地址
    		out.newLine();//换行
    		out.close();//关闭文件
    		flag++;//服务器个数加1
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	public serverAddress(){
		Container con =this.getContentPane();//初始化一个容器
		txtMsg.setEditable(true);//允许编辑文本区
		btnSend.setEnabled(true);//允许启用“输入”按钮
		btnStop.setEnabled(true);//允许启用“输入完毕”按钮
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
		setTitle("服务器端地址");
		setVisible(true);//显示窗口
	}
	public void actionPerformed(ActionEvent e)//事件触发器，单击鼠标触发
	{
		if(e.getSource()==btnSend ||e.getSource()==txtSendMsg){//输入服务器IP，并重置文本框
			infile(txtSendMsg.getText());
			txtMsg.append("服务器IP地址："+txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}else if(e.getSource()==btnStop){
			dispose();//关闭窗口
			new matrix();
		}
	}
	public static void main(String args[]){
		new serverAddress();
	}
}

