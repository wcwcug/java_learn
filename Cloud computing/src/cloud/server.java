package cloud;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class server{
	ServerSocket server; 
	Socket sock; 
	DataOutputStream out2;
	DataInputStream in2;
	Waiter2 waiter;
	String infile1="D:\\Data.txt";//����һ���ļ�
	int[][]A=new int[2][4];
	int[][]B=new int[4][4];
	int[][]C=new int[2][4];
	public server(){
		connect();
		inPut();
		calculate();
		
	}
	public void connect(){//��������
		try {
			server=new ServerSocket(8700);
			sock=server.accept();
			System.out.println("�����˿ڡ�������");
			OutputStream os=sock.getOutputStream();//�����׽��ֻ�������
			InputStream is=sock.getInputStream();//�����׽��ֻ��������
			out2 =new DataOutputStream(os);//�����׽��ֽ������������
			in2=new DataInputStream(is);//�����׽��ֽ�������������	
			waiter =new Waiter2();
			waiter.start();
			BufferedWriter out1=new BufferedWriter(new FileWriter(infile1,true));
			out1.close();


		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	class Waiter2 extends Thread{//������Ϣ���߳���
		public void run(){
			try{
				while(true){
					String msg;
					msg=in2.readUTF();
					try {
			    		BufferedWriter outa=new BufferedWriter(new FileWriter(infile1,true));
			    		outa.write(msg);//д����Ϣ
			    		outa.newLine();//����
			    		outa.close();//�ر��ļ�
			    	} catch (IOException e) {

			    	}
					}
				
			}catch(Exception e){
				
			}
		}
		
	}
	public void inPut(){//��������ֵ
		try {
			Scanner in2 = new Scanner(new File("D:\\Data.txt"));
			int aData;
//			System.out.println("vyugyvy8vyugyvy8vyugyvy8");
			for(int i=0;i<2;i++)
				for(int j=0;j<4;j++)
				{
					aData=in2.nextInt();//��ȡһ������
					A[i][j]=aData;
//					System.out.println(A[i][j]+"g9ugg");

				}
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
				{
					aData=in2.nextInt();//��ȡһ������
					B[i][j]=aData;
//					System.out.println(B[i][j]+"buigbuig");
				}
			in2.close();
//			System.out.println("vyugyvy8vyugyvy8vyugyvy8");
		} catch (Exception e) {
			
		}
	}
	public void calculate(){//��������������ͻ���
		for(int i=0;i<2;i++)
			for(int j=0;j<4;j++)
			{				
				C[i][j]=A[i][0]*B[0][j]+A[i][1]*B[1][j]+A[i][2]*B[2][j]+A[i][3]*B[3][j];//����˷�
				try {
					out2.write(C[i][j]);
//					System.out.println(C[i][j]+"vyugyvy8");
				} catch (IOException e) {
					
				}
			}
	}
	public static void main(String args[])
	{
		new server();
	}
}
