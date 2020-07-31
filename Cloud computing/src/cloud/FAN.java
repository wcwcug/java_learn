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
		setTitle("�ӽڵ�");
		setBounds(100,100,350,400);          //���ô��ڵĴ�С
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void run(){
		receiveData=new TextArea();
		getContentPane().add(receiveData);
		receiveData.setBounds(5, 5, 325, 350);      //������ʾ�ӽڵ��λ��
		receiveData.append("�ӽڵ����ڵȴ�����......"+"\n\n\n\n");
		try{
			//����ServerSocket���󣬼���������PORT�˿�
			ServerSocket serverSocket=new ServerSocket(PORT);
			//����socket�׽��֣�����ͻ�����������
			Socket client=serverSocket.accept();
			//�ȴ����ܿͻ��˵������������ӳɹ��󷵻�һ�������ӵ�Socket����
			//�����������������ڶ�ȡ�ͻ��˷��͵���Ϣ
			BufferedReader cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
			//��������������������ͻ��˷�����Ϣ
			PrintWriter cout=new PrintWriter(client.getOutputStream());	
			int k=cin.read();int n=cin.read();
			int A[][]=new int[k][n];int B[][]=new int[n][n];
			receiveData.append("���տͻ����������ľ���A[]: "+"\n");
			for(int i=0;i<k;i++){
				for(int j=0;j<n;j++){
					A[i][j]=cin.read();
					receiveData.append("   "+A[i][j]);
				}
				receiveData.append("\n");
			}
			receiveData.append("\n"+"���տͻ����������ľ���B[]: "+"\n");
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
			receiveData.append("\n"+"���������ظ��ͻ�����");
		}catch(Exception e){
			System.out.println("�������˳�����Ϣ���£�\n"+e.getMessage());
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
			serv.setVisible(true);           //��ʾ����
			serv.run();
		}catch(Exception a){
			a.printStackTrace();
		}
	}
}
