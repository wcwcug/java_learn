package chat;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
public class Server {//��������
    boolean started = false;
    ServerSocket ss = null;
    List<Client1> clients = new ArrayList<Client1>(); //����ͻ����߳���
	String infile1="D:\\chatData.txt";//����һ���ļ�
    void infile(String data){//д�ļ�

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile1,true));
    		out.write(data);//д��������Ϣ
    		out.newLine();//����
    		out.close();//�ر��ļ�
    	} catch (IOException e) {
    		e.printStackTrace();
    	}}
    void start() {//��������˲����տͻ���
        try {
            ss = new ServerSocket(8800); //��������˶���
            started = true;
        } catch (BindException e) {
            System.out.println("�˿�ʹ����");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (started) {
                Socket s = ss.accept(); //���տͻ���
                Client1 c = new Client1(s);
                System.out.println("�ɹ����տ͑���");
                new Thread(c).start(); //�����߳�
                clients.add(c); //����߳���
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }      
      
    }
    class Client1 implements Runnable { //�����ͻ����߳̽��գ���������
    	private Socket s;
    	DataInputStream in = null;//��������������
        DataOutputStream out = null;//�������������
        boolean isConnected = false;
        public Client1(Socket s) {//���캯��
            this.s = s;
            try {
                in = new DataInputStream(s.getInputStream());//������������������������
                out = new DataOutputStream(s.getOutputStream());//����������������������
                isConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void sendAll(String str) {//����������Ϣ
        	
            try {           	
                out.writeUTF(str);//����Ϣ������ͻ���
                
            	
            } catch (SocketException e) {
                System.out.println("�û��˳�������");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      
        public void run() {
            try {
                while (isConnected) {
                    String str = in.readUTF();//����������
                    infile(str);//д���ļ�
                    System.out.println(str);
                  for(Client1 e:clients){//�������
                	  
                	  e.sendAll(str);
                	
                  }
                    
                }
            } catch (EOFException e) {
                System.out.println("�û��˳�������");
                isConnected=false;
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null)
                    if (s != null)
                        try {
                            in.close();
                            s.close();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            }
        }
    }
    public static void main(String[] args) {
        new Server().start();
    }
}

