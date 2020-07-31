package cloud;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
public class client extends JFrame {//客户端
	JTextArea txtMsg1 = new JTextArea();//构造一个新的文本区
	JTextArea txtMsg2 = new JTextArea();//构造一个新的文本区
	JTextArea txtMsg3 = new JTextArea();//构造一个新的文本区
	DataOutputStream out;//定义数据输出流
	DataInputStream in;//定义数据输入流
	Socket sock;
	String line;
	Waiter waiter;
	int flag=0;
	int d[]=new int[16];
	int e[]=new int[16];
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
	public void outfile(){//读取矩阵数据信息并发送给服务器
		try{
			/*读取矩阵A数据信息*/
			FileReader fin=new FileReader("D:\\A.txt");
			Scanner in1 = new Scanner(fin);
			int aa=0;			
			while(in1.hasNextInt()){
				d[aa]=in1.nextInt();
				aa++;
			}
			/*读取矩阵B数据信息*/
			FileReader fin2=new FileReader("D:\\B.txt");
			Scanner in2 = new Scanner(fin2);
			int bb=0;			
			while(in2.hasNextInt()){
				e[bb]=in2.nextInt();
				bb++;
			}
			
			/*发送矩阵A的数据信息给服务器*/
			if(flag==0){
				txtMsg2.append("发送矩阵A[]的数据：\n");
				for(int i=0;i<8;i++){
					out.write(d[i]);
					if(i%4==3){//换行
						txtMsg2.append(d[i]+"\n");
						}
					else{
						txtMsg2.append(d[i]+"  ");
					}
					}
				flag++;
			}
			else{
				txtMsg2.append("发送矩阵A[]的数据：\n");
				for(int i=8;i<16;i++){
					out.write(d[i]);
					if(i%4==3){//换行
						txtMsg2.append(d[i]+"\n");
						}
					else{
						txtMsg2.append(d[i]+"  ");
					}
					}
			}
			/*发送矩阵A的数据信息给服务器*/
			txtMsg2.append("\n发送矩阵B[]的数据：\n");
			for(int j=0;j<16;j++){
				out.write(e[j]);
				if(j%4==3){//换行
					txtMsg2.append(d[j]+"\n");
					}
				else{
					txtMsg2.append(d[j]+"  ");
				}
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
			txtMsg2.append("\n正在发送数据给子节点： "+line+"\n");
			outfile();			
			/*创建并启动线程*/
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
		link();//读取服务器IP地址并创建连接
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
			int k=0;					
			while(true){
				try{
					msg=in.read();//复制输入流上的内容
					k++;					
					if(k%4==0){//换行
						txtMsg3.append(msg+"\n ");//输出到用户界面
					}
					else{
						txtMsg3.append(msg+" ");//输出到用户界面
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



