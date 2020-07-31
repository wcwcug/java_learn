package cloud;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
public class client extends JFrame {
	JTextArea txtMsg1 = new JTextArea();//构造一个新的文本区
	JTextArea txtMsg2 = new JTextArea();//构造一个新的文本区
	JTextArea txtMsg3 = new JTextArea();//构造一个新的文本区
	DataOutputStream out;//定义数据输出流
	DataInputStream in;//定义数据输入流
	Socket sock;
	String line;
	Waiter waiter;
	int flag=0;
	String outfile1="D:\\node.txt";//读一个文件
	public void link(){//读取服务器IP地址并创建连接
		try{
			BufferedReader in =new BufferedReader(new FileReader(outfile1));	
			
			line=in.readLine();//读取一行内容		
			while(line!=null){
				connect(line);
				txtMsg1.append("连接到子节点： "+line+"\n");
				line=in.readLine();
    		}
    		in.close();
    	}catch(IOException ex){
    		
    	}
	}
	public void outfile() {//读取矩阵A,B,并传送数据
		try{
			if(flag==0){
				String outfile1="D:\\A.txt";//读一个文件
				txtMsg2.append("发送矩阵A[]的数据：\n");
				BufferedReader in1 =new BufferedReader(new FileReader(outfile1));
				for(int i=0;i<2;i++){
					String line1;
					line1=in1.readLine();//读取一行内容
					out.writeUTF(line1);
					txtMsg2.append(line1+"\n");
				}
				flag++;
			}
			else{
				String outfile1="D:\\A.txt";//读一个文件
				txtMsg2.append("发送矩阵A[]的数据：\n");
				BufferedReader in1 =new BufferedReader(new FileReader(outfile1));
				for(int i=0;i<2;i++){
					String line1;
					line1=in1.readLine();//读取一行内容
				}
				for(int k=0;k<2;k++){		
					String line1;
					line1=in1.readLine();//读取一行内容
					out.writeUTF(line1);					
					txtMsg2.append(line1+"\n");
				}
			}
			String outfile2="D:\\B.txt";//读一个文件
			txtMsg2.append("发送矩阵B[]的数据：\n");
			BufferedReader in2 =new BufferedReader(new FileReader(outfile2));
			for(int i=0;i<4;i++){
				String line2;
				line2=in2.readLine();//读取一行内容
				out.writeUTF(line2);
				txtMsg2.append(line2+"\n");
			}

		}catch(Exception e){
			
		}
	}

	public void connect(String line){//连接服务器子节点
		try {//建立与服务器连接的套接字
			sock=new Socket(line,8700);
			OutputStream os=sock.getOutputStream();//根据套接字获得输出流
			InputStream is=sock.getInputStream();//根据套接字获得输入流
			out =new DataOutputStream(os);//根据套接字建立数据输出流
			in=new DataInputStream(is);//根据套接字建立数据输入流	
			txtMsg2.append("正在发送数据给子节点： "+line+"\n");
			outfile();			
//			txtMsg3.append("正在接收子节点 "+line+" 传回的数据：\n ");//输出到用户界面
			waiter=new Waiter();
			waiter.start();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public client() //构造函数
	{
		/*界面设计*/
		super("简易云计算系统");	
		Container con =this.getContentPane();//初始化一个容器
		JScrollPane p1=new JScrollPane(txtMsg1);//滚动面板1
		JScrollPane p2=new JScrollPane(txtMsg2);//滚动面板2
		JScrollPane p3=new JScrollPane(txtMsg3);//滚动面板3

		con.add(p1,BorderLayout.NORTH);//面板所在位置
		con.add(p2,BorderLayout.CENTER);//面板所在位置
		con.add(p3,BorderLayout.SOUTH);//面板所在位置
		
		txtMsg3.append("接收子节点传回的数据...\n\n ");//输出到用户界面
		txtMsg3.append("矩阵运算结果：\n ");//输出到用户界面
//		link();//读取服务器IP地址并创建连接
//		connect("127.0.0.1");
		connect("192.168.0.116");
//		connect("192.168.0.115");

		setSize(500,500);
		setVisible(true);//显示窗口
		setResizable(true);//窗口可改变
		addWindowListener(new WindowAdapter()//frame追加一个windows事件监听
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();//关闭窗体释放该窗体的占用的屏幕资源
				System.exit(0);
			}
		});
	}
	private class Waiter extends Thread{//用于接收消息的线程
		public void run(){
			int msg;
//			txtMsg3.append("矩阵运算：\n ");//输出到用户界面
			int k=0;					
			while(true){
				try{
					msg=in.read();//复制输入流上的内容
//					txtMsg3.append(msg+" ");//输出到用户界面
					k++;					
					if(k%4==0){
						txtMsg3.append(msg+"\n ");//输出到用户界面
//						System.out.println(msg);
					}
					else{
						txtMsg3.append(msg+" ");//输出到用户界面
//						System.out.println(msg);
					}
					
				}catch(IOException ex){
					break;
				}
			}
		}
	}


	public static void main(String args[])
	{
			new client();

	}
}


