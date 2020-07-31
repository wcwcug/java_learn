package cloud;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
public class client extends JFrame {//�ͻ���
	JTextArea txtMsg1 = new JTextArea();//����һ���µ��ı���
	JTextArea txtMsg2 = new JTextArea();//����һ���µ��ı���
	JTextArea txtMsg3 = new JTextArea();//����һ���µ��ı���
	DataOutputStream out;//�������������
	DataInputStream in;//��������������
	Socket sock;
	String line;
	Waiter waiter;
	int flag=0;
	int d[]=new int[16];
	int e[]=new int[16];
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
	public void outfile(){//��ȡ����������Ϣ�����͸�������
		try{
			/*��ȡ����A������Ϣ*/
			FileReader fin=new FileReader("D:\\A.txt");
			Scanner in1 = new Scanner(fin);
			int aa=0;			
			while(in1.hasNextInt()){
				d[aa]=in1.nextInt();
				aa++;
			}
			/*��ȡ����B������Ϣ*/
			FileReader fin2=new FileReader("D:\\B.txt");
			Scanner in2 = new Scanner(fin2);
			int bb=0;			
			while(in2.hasNextInt()){
				e[bb]=in2.nextInt();
				bb++;
			}
			
			/*���;���A��������Ϣ��������*/
			if(flag==0){
				txtMsg2.append("���;���A[]�����ݣ�\n");
				for(int i=0;i<8;i++){
					out.write(d[i]);
					if(i%4==3){//����
						txtMsg2.append(d[i]+"\n");
						}
					else{
						txtMsg2.append(d[i]+"  ");
					}
					}
				flag++;
			}
			else{
				txtMsg2.append("���;���A[]�����ݣ�\n");
				for(int i=8;i<16;i++){
					out.write(d[i]);
					if(i%4==3){//����
						txtMsg2.append(d[i]+"\n");
						}
					else{
						txtMsg2.append(d[i]+"  ");
					}
					}
			}
			/*���;���A��������Ϣ��������*/
			txtMsg2.append("\n���;���B[]�����ݣ�\n");
			for(int j=0;j<16;j++){
				out.write(e[j]);
				if(j%4==3){//����
					txtMsg2.append(d[j]+"\n");
					}
				else{
					txtMsg2.append(d[j]+"  ");
				}
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
			txtMsg2.append("\n���ڷ������ݸ��ӽڵ㣺 "+line+"\n");
			outfile();			
			/*�����������߳�*/
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
		link();//��ȡ������IP��ַ����������
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
			int k=0;					
			while(true){
				try{
					msg=in.read();//�����������ϵ�����
					k++;					
					if(k%4==0){//����
						txtMsg3.append(msg+"\n ");//������û�����
					}
					else{
						txtMsg3.append(msg+" ");//������û�����
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



