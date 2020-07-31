package cloud;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
public class client extends JFrame {
	JTextArea txtMsg1 = new JTextArea();//����һ���µ��ı���
	JTextArea txtMsg2 = new JTextArea();//����һ���µ��ı���
	JTextArea txtMsg3 = new JTextArea();//����һ���µ��ı���
	DataOutputStream out;//�������������
	DataInputStream in;//��������������
	Socket sock;
	String line;
	Waiter waiter;
	int flag=0;
	String outfile1="D:\\node.txt";//��һ���ļ�
	public void link(){//��ȡ������IP��ַ����������
		try{
			BufferedReader in =new BufferedReader(new FileReader(outfile1));	
			
			line=in.readLine();//��ȡһ������		
			while(line!=null){
				connect(line);
				txtMsg1.append("���ӵ��ӽڵ㣺 "+line+"\n");
				line=in.readLine();
    		}
    		in.close();
    	}catch(IOException ex){
    		
    	}
	}
	public void outfile() {//��ȡ����A,B,����������
		try{
			if(flag==0){
				String outfile1="D:\\A.txt";//��һ���ļ�
				txtMsg2.append("���;���A[]�����ݣ�\n");
				BufferedReader in1 =new BufferedReader(new FileReader(outfile1));
				for(int i=0;i<2;i++){
					String line1;
					line1=in1.readLine();//��ȡһ������
					out.writeUTF(line1);
					txtMsg2.append(line1+"\n");
				}
				flag++;
			}
			else{
				String outfile1="D:\\A.txt";//��һ���ļ�
				txtMsg2.append("���;���A[]�����ݣ�\n");
				BufferedReader in1 =new BufferedReader(new FileReader(outfile1));
				for(int i=0;i<2;i++){
					String line1;
					line1=in1.readLine();//��ȡһ������
				}
				for(int k=0;k<2;k++){		
					String line1;
					line1=in1.readLine();//��ȡһ������
					out.writeUTF(line1);					
					txtMsg2.append(line1+"\n");
				}
			}
			String outfile2="D:\\B.txt";//��һ���ļ�
			txtMsg2.append("���;���B[]�����ݣ�\n");
			BufferedReader in2 =new BufferedReader(new FileReader(outfile2));
			for(int i=0;i<4;i++){
				String line2;
				line2=in2.readLine();//��ȡһ������
				out.writeUTF(line2);
				txtMsg2.append(line2+"\n");
			}

		}catch(Exception e){
			
		}
	}

	public void connect(String line){//���ӷ������ӽڵ�
		try {//��������������ӵ��׽���
			sock=new Socket(line,8700);
			OutputStream os=sock.getOutputStream();//�����׽��ֻ�������
			InputStream is=sock.getInputStream();//�����׽��ֻ��������
			out =new DataOutputStream(os);//�����׽��ֽ������������
			in=new DataInputStream(is);//�����׽��ֽ�������������	
			txtMsg2.append("���ڷ������ݸ��ӽڵ㣺 "+line+"\n");
			outfile();			
//			txtMsg3.append("���ڽ����ӽڵ� "+line+" ���ص����ݣ�\n ");//������û�����
			waiter=new Waiter();
			waiter.start();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public client() //���캯��
	{
		/*�������*/
		super("�����Ƽ���ϵͳ");	
		Container con =this.getContentPane();//��ʼ��һ������
		JScrollPane p1=new JScrollPane(txtMsg1);//�������1
		JScrollPane p2=new JScrollPane(txtMsg2);//�������2
		JScrollPane p3=new JScrollPane(txtMsg3);//�������3

		con.add(p1,BorderLayout.NORTH);//�������λ��
		con.add(p2,BorderLayout.CENTER);//�������λ��
		con.add(p3,BorderLayout.SOUTH);//�������λ��
		
		txtMsg3.append("�����ӽڵ㴫�ص�����...\n\n ");//������û�����
		txtMsg3.append("������������\n ");//������û�����
//		link();//��ȡ������IP��ַ����������
//		connect("127.0.0.1");
		connect("192.168.0.116");
//		connect("192.168.0.115");

		setSize(500,500);
		setVisible(true);//��ʾ����
		setResizable(true);//���ڿɸı�
		addWindowListener(new WindowAdapter()//frame׷��һ��windows�¼�����
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();//�رմ����ͷŸô����ռ�õ���Ļ��Դ
				System.exit(0);
			}
		});
	}
	private class Waiter extends Thread{//���ڽ�����Ϣ���߳�
		public void run(){
			int msg;
//			txtMsg3.append("�������㣺\n ");//������û�����
			int k=0;					
			while(true){
				try{
					msg=in.read();//�����������ϵ�����
//					txtMsg3.append(msg+" ");//������û�����
					k++;					
					if(k%4==0){
						txtMsg3.append(msg+"\n ");//������û�����
//						System.out.println(msg);
					}
					else{
						txtMsg3.append(msg+" ");//������û�����
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


