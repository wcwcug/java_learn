package cloud;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class server{//��������
	ServerSocket server;
	Socket sock; 
	DataOutputStream out2;//�������������
	DataInputStream in2;//��������������
	Waiter2 waiter;
	int[][]A=new int[2][4];//����A��������
	int[][]B=new int[4][4];//����B��������
	int[][]C=new int[2][4];//���������
	int[]Q=new int[24];//�������Կͻ��˵�������Ϣ
	int flag=0;
	boolean blag=true;

	public server(){//��������
		try {
			server=new ServerSocket(8700);
			sock=server.accept();//�����˿�
			System.out.println("�����˿��С�����");
			OutputStream os=sock.getOutputStream();//�����׽��ֻ�������
			InputStream is=sock.getInputStream();//�����׽��ֻ��������
			out2 =new DataOutputStream(os);//�����׽��ֽ������������
			in2=new DataInputStream(is);//�����׽��ֽ�������������	
			/*�����������߳�*/
			waiter =new Waiter2();
			waiter.start();			


		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	class Waiter2 extends Thread{//������Ϣ���߳���
		public void run(){
			int kk=0;
			try{
				while(blag){
					int msg;
					msg=in2.read();//�����������ϵ�����
					
			    		Q[kk]=msg;		
			    		kk++;
			    		if(kk==24){//���վ���������Ϣ��ϣ���������������ͻ���
			    			calculate();//����
			    		}
					}
				
				
			}catch(Exception e){
				
			}
		}
		
	}
	public void calculate(){//��������������ͻ���
		int aData=0;
		/*����A������Ϣ*/
		for(int i=0;i<2;i++)
			for(int j=0;j<4;j++)
			{
				A[i][j]=Q[aData];
				aData++;

			}
		/*����B������Ϣ*/
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
			{
				B[i][j]=Q[aData];
				aData++;
			}
		System.out.println("��������(IP��ַΪ192.168.0.116��������Ϊ��");
		/*��������*/
		for(int i=0;i<2;i++)
			for(int j=0;j<4;j++)
			{				
				C[i][j]=A[i][0]*B[0][j]+A[i][1]*B[1][j]+A[i][2]*B[2][j]+A[i][3]*B[3][j];//����˷�
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
