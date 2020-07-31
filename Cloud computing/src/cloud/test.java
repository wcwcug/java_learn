package cloud;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class test{
	ServerSocket server;
	Socket sock;
	DataOutputStream out2;
	DataInputStream in2;
	Waiter2 waiter;
	String infile1="D:\\Data.txt";//创建一个文件
	int[][]A=new int[2][4];
	int[][]B=new int[4][4];
	int[][]C=new int[4][4];
	public test(){
		connect();
		calculate();
	}
	public void connect(){//创建连接
		try {
			server=new ServerSocket(8700);
			sock=server.accept();
			OutputStream os=sock.getOutputStream();//根据套接字获得输出流
			InputStream is=sock.getInputStream();//根据套接字获得输入流
			out2 =new DataOutputStream(os);//根据套接字建立数据输出流
			in2=new DataInputStream(is);//根据套接字建立数据输入流	

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
				while(true){
					String msg;
					msg=in2.readUTF();
					try {
			    		BufferedWriter outa=new BufferedWriter(new FileWriter(infile1,true));
			    		outa.write(msg);//写入聊天信息
			    		outa.newLine();//换行
			    		outa.close();//关闭文件
			    	} catch (IOException e) {

			    	}
					}
				
			}catch(Exception e){
				
			}
		}
		
	}
	public void calculate(){//计算结果并输出给客户端
		try {
			Scanner in2 = new Scanner(new File("D:\\Data.txt"));
			int aData;
			for(int i=0;i<2;i++)
				for(int j=0;j<4;j++)
				{
					aData=in2.nextInt();//读取一个数据
					A[i][j]=aData;
//					System.out.println(A[i][j]);

				}
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
				{
					aData=in2.nextInt();//读取一个数据
					B[i][j]=aData;
//					System.out.println(B[i][j]);
				}
		} catch (FileNotFoundException e1) {
			
		}



		for(int i=0;i<2;i++)
			for(int j=0;j<4;j++)
			{				
				C[i][j]=A[i][0]*B[0][j]+A[i][1]*B[1][j]+A[i][2]*B[2][j]+A[i][3]*B[3][j];//矩阵乘法
				try {
					out2.write(C[i][j]);
				} catch (IOException e) {
					
				}
			}
	}
	public static void main(String args[])
	{
		new test();
	}
}
