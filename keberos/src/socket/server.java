package socket;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class server {

	public static int PORT = 8080;
	void start()//服务器端监听端口
	{
		ServerSocket s=null;
		Socket socket = new Socket() ;
		try {
			s = new ServerSocket(PORT);
			//等待新请求、否则一直阻塞
			while(true){
				socket = s.accept();
				System.out.println("socket:"+socket);
				new serverone(socket);
				
			}
		} catch (Exception e) {
			try {
				socket.close();
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		}finally{
             if (s != null)
			try {
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}
	
	public static void main(String[] args) {
		server a=new server();
		a.start();
	}

}


