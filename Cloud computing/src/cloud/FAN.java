package cloud;
import java.awt.TextArea;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
public class FAN extends JFrame{
	public static final int PORT=6789;
	TextArea receiveData;
	public FAN(){
		super();
		getContentPane().setLayout(null);
		setTitle("子节点");
		setBounds(100,100,350,400);          //设置窗口的大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void run(){
		receiveData=new TextArea();
		getContentPane().add(receiveData);
		receiveData.setBounds(5, 5, 325, 350);      //设置显示子节点的位置
		receiveData.append("子节点正在等待连接......"+"\n\n\n\n");
		try{
			//创建ServerSocket对象，监听本机的PORT端口
			ServerSocket serverSocket=new ServerSocket(PORT);
			//创建socket套接字，处理客户端连接请求
			Socket client=serverSocket.accept();
			//等待接受客户端的连接请求，连接成功后返回一个已连接的Socket对象
			//创建缓冲区对象，用于读取客户端发送的信息
			BufferedReader cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
			//创建缓冲区对象，用于向客户端发送信息
			PrintWriter cout=new PrintWriter(client.getOutputStream());	
			int k=cin.read();int n=cin.read();
			int A[][]=new int[k][n];int B[][]=new int[n][n];
			receiveData.append("接收客户机发送来的矩阵A[]: "+"\n");
			for(int i=0;i<k;i++){
				for(int j=0;j<n;j++){
					A[i][j]=cin.read();
					receiveData.append("   "+A[i][j]);
				}
				receiveData.append("\n");
			}
			receiveData.append("\n"+"接收客户机发送来的矩阵B[]: "+"\n");
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					B[i][j]=cin.read();
					receiveData.append("   "+B[i][j]);
				}
				receiveData.append("\n");
			}
			int C[][]=new int[k][n];
			C=Calculate(A,B);
			for(int i=0;i<k;i++){
				for(int j=0;j<n;j++){
					cout.write(C[i][j]);
				}
			}
			cout.flush();
			receiveData.append("\n"+"计算结果发回给客户机。");
		}catch(Exception e){
			System.out.println("服务器端出错，信息如下：\n"+e.getMessage());
		}
	}
	public int[][] Calculate(int A[][],int B[][]){
		int a=A.length;int b=B[0].length;
		int C[][]=new int[a][b];
		for(int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				for(int k=0;k<b;k++){
					C[i][j]+=A[i][k]*B[k][j];
				}
			}
		}
		return C;
	}
	public static void main(String[]args){
		FAN serv=new FAN();
		serv.run();
		try{
			serv.setVisible(true);           //显示窗口
			serv.run();
		}catch(Exception a){
			a.printStackTrace();
		}
	}
}
