package cloud;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class server{//服务器端
	ServerSocket server;
	Socket sock; 
	DataOutputStream out2;//定义数据输出流
	DataInputStream in2;//定义数据输入流
	Waiter2 waiter;
	int[][]A=new int[2][4];//保存A矩阵数据
	int[][]B=new int[4][4];//保存B矩阵数据
	int[][]C=new int[2][4];//保存计算结果
	int[]Q=new int[24];//保存来自客户端的数据信息
	int flag=0;
	boolean blag=true;

	public server(){//创建连接
		try {
			server=new ServerSocket(8700);
			sock=server.accept();//监听端口
			System.out.println("监听端口中。。。");
			OutputStream os=sock.getOutputStream();//根据套接字获得输出流
			InputStream is=sock.getInputStream();//根据套接字获得输入流
			out2 =new DataOutputStream(os);//根据套接字建立数据输出流
			in2=new DataInputStream(is);//根据套接字建立数据输入流	
			/*创建并启动线程*/
			waiter =new Waiter2();
			waiter.start();			


		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	class Waiter2 extends Thread{//接收消息的线程类
		public void run(){
			int kk=0;
			try{
				while(blag){
					int msg;
					msg=in2.read();//复制输入流上的内容
					
			    		Q[kk]=msg;		
			    		kk++;
			    		if(kk==24){//接收矩阵数据信息完毕，计算结果并输出给客户端
			    			calculate();//计算
			    		}
					}
				
				
			}catch(Exception e){
				
			}
		}
		
	}
	public void calculate(){//计算结果并输出给客户端
		int aData=0;
		/*填入A矩阵信息*/
		for(int i=0;i<2;i++)
			for(int j=0;j<4;j++)
			{
				A[i][j]=Q[aData];
				aData++;

			}
		/*填入B矩阵信息*/
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
			{
				B[i][j]=Q[aData];
				aData++;
			}
		System.out.println("本服务器(IP地址为192.168.0.116）计算结果为：");
		/*矩阵运算*/
		for(int i=0;i<2;i++)
			for(int j=0;j<4;j++)
			{				
				C[i][j]=A[i][0]*B[0][j]+A[i][1]*B[1][j]+A[i][2]*B[2][j]+A[i][3]*B[3][j];//矩阵乘法
				try {
					out2.write(C[i][j]);
					System.out.print(C[i][j]+" ");
				} catch (IOException e) {
					
				}
			}
	}
	public static void main(String args[])
	{
		new server();
	}
}
