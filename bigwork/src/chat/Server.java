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
public class Server {//服务器端
    boolean started = false;
    ServerSocket ss = null;
    List<Client1> clients = new ArrayList<Client1>(); //保存客户端线程类
	String infile1="D:\\chatData.txt";//创建一个文件
    void infile(String data){//写文件

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile1,true));
    		out.write(data);//写入聊天信息
    		out.newLine();//换行
    		out.close();//关闭文件
    	} catch (IOException e) {
    		e.printStackTrace();
    	}}
    void start() {//建立服务端并接收客户端
        try {
            ss = new ServerSocket(8800); //建立服务端对象
            started = true;
        } catch (BindException e) {
            System.out.println("端口使用中");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (started) {
                Socket s = ss.accept(); //接收客户端
                Client1 c = new Client1(s);
                System.out.println("成功接收客戶端");
                new Thread(c).start(); //启动线程
                clients.add(c); //添加线程类
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
    class Client1 implements Runnable { //建立客户端线程接收，发送数据
    	private Socket s;
    	DataInputStream in = null;//定义数据输入流
        DataOutputStream out = null;//定义数据输出流
        boolean isConnected = false;
        public Client1(Socket s) {//构造函数
            this.s = s;
            try {
                in = new DataInputStream(s.getInputStream());//根据输入流建立数据输入流
                out = new DataOutputStream(s.getOutputStream());//根据输出流建立数据输出流
                isConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void sendAll(String str) {//发出聊天消息
        	
            try {           	
                out.writeUTF(str);//将消息输出给客户端
                
            	
            } catch (SocketException e) {
                System.out.println("用户退出聊天室");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      
        public void run() {
            try {
                while (isConnected) {
                    String str = in.readUTF();//读出输入流
                    infile(str);//写入文件
                    System.out.println(str);
                  for(Client1 e:clients){//遍历输出
                	  
                	  e.sendAll(str);
                	
                  }
                    
                }
            } catch (EOFException e) {
                System.out.println("用户退出聊天室");
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

