package chat;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
class Client extends JFrame implements ActionListener{//用户端
	Socket sock;
	JTextArea txtMsg = new JTextArea();//构造一个新的文本区
	JTextField txtSendMsg=new JTextField(20);//构造了一个宽度为 20 个字符的单行文本框 
	JButton btnSend=new JButton("发送");//建立一个发送按钮
	JButton btnConnect=new JButton("进入聊天室");//建立“进入聊天室”按钮
	JButton btnDisConnect=new JButton("退出聊天室");//建立“退出聊天室”按钮
	DataOutputStream out;//定义数据输出流
	DataInputStream in;//定义数据输入流
	boolean canWaiter=true;
	Waiter waiter;//创建一个线程
	private String name1;
	public void outfile(){//读取消息记录
		try{
			String outfile1="D:\\chatData.txt";//读一个文件
			BufferedReader in =new BufferedReader(new FileReader(outfile1));
			String line;
			line=in.readLine();//读取一行内容
			while(line!=null){
    		txtMsg.append("***    "+line);
    		txtMsg.append("\n");
    		line=in.readLine();
    		}
    		in.close();
    	}catch(IOException ex){
    		
    	}
	}
	public Client(String name)
	{
		name1=name;//用户名
		//System.out.println(name1);
		Container con =this.getContentPane();//初始化一个容器
		txtMsg.setEditable(false);//禁止编辑文本区
		btnConnect.setEnabled(true);//允许启用“连接服务器”按钮
		btnDisConnect.setEnabled(false);//禁止启用“断开连接”按钮
		btnSend.setEnabled(false);//禁止启用“发送”按钮
		txtSendMsg.setEditable(false);//禁止编辑文本框
		/*利用JPanel容器管理界面*/
		JPanel p1=new JPanel();
		JScrollPane p2=new JScrollPane(txtMsg);//滚动面板
		JPanel p3=new JPanel();
		p1.add(btnConnect);
		p1.add(btnDisConnect);
		p3.add(txtSendMsg);
		p3.add(btnSend);		
		con.add(p1, "North");
		con.add(p2, "Center");
		con.add(p3, "South");
		/*监听*/
		txtSendMsg.addActionListener(this);
		btnSend.addActionListener(this);
		btnConnect.addActionListener(this);
		btnDisConnect.addActionListener(this);

		addWindowListener(new WindowAdapter(){//frame追加一个windows事件监听
			public void windowClosing(WindowEvent e){
				try{
					disconnect();//断开连接
				}catch(Exception ee){					
				}
				dispose();//关闭窗体释放该窗体的占用的屏幕资源
				System.exit(0);	//停止线程			
			}
		});
		//setTitle("王家小菜聊天室");
		setSize(400,400);
		setVisible(true);//显示窗口
		connect();//连接服务器
	}
	private void connect(){//连接服务器
		try{
			sock=new Socket("127.0.0.1",8800);//建立与服务器连接的套接字
			//System.out.println("登陆界面");
			OutputStream os=sock.getOutputStream();//根据套接字获得输出流
			InputStream is=sock.getInputStream();//根据套接字获得输入流
			out =new DataOutputStream(os);//根据套接字建立数据输出流
			in=new DataInputStream(is);//根据套接字建立数据输入流
//			txtMsg.append(name1+"   登陆聊天室成功\n");//在客户端文本框输出连接情况
			setTitle(name1);
			txtMsg.append("*****聊天室消息记录*****\n");
			outfile();//读消息记录
			txtMsg.append("\n\n\n\n\n");
			out.writeUTF("【系统消息】"+name1+"进入聊天室\n");
			btnConnect.setEnabled(false);//禁止启用“连接服务器”按钮
			btnDisConnect.setEnabled(true);//允许启用“断开连接”按钮
			btnSend.setEnabled(true);//允许启用“发送”按钮
			txtSendMsg.setEditable(true);//允许编辑文本框
			waiter =new Waiter();//建立线程
			waiter.start();//启动线程
		}catch(Exception ee){
			JOptionPane.showMessageDialog(null, "连接服务器失败！");
		}
	}
	private void sendMsg(){//发送消息
			try{
				SimpleDateFormat formater=new SimpleDateFormat("HH:mm:ss");//输出实时时间
				String time=formater.format(new Date());
				out.writeUTF(time+"  "+name1+"："+txtSendMsg.getText());//向服务器发送消息
			}catch(Exception ee){
				JOptionPane.showMessageDialog(null, "发送消息失败！");
			}
		}
	private void disconnect(){//断开连接
		btnConnect.setEnabled(true);//允许启用“连接服务器”按钮
		btnDisConnect.setEnabled(false);//禁止启用“断开连接”按钮
		btnSend.setEnabled(false);//禁止启用“发送”按钮
		txtSendMsg.setEditable(false);//禁止编辑文本框
		try{
			out.writeUTF("【系统消息】"+name1+"退出聊天室\n");//写入输出流
		}catch(Exception ex){			
		}finally{
			canWaiter=false;
			try{
				in.close();//关闭输入流
				out.close();//关闭输出流
			}catch(Exception ex){			
			}finally{
				try{
					sock.close();//关闭套接字
				}catch(Exception ex){			
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e)//事件触发器，单击鼠标触发
	{
		if(e.getSource()==btnSend ||e.getSource()==txtSendMsg){//发送消息或编辑文本框，并重置文本框
			sendMsg();
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}else if(e.getSource()==btnConnect){//连接服务器
			canWaiter=true;
			connect();
		}else if(e.getSource()==btnDisConnect){//断开连接
			disconnect();
		}
	}
	private class Waiter extends Thread{//用于接收消息的线程
		public void run(){
			String msg=null;
			while(canWaiter){
				try{
					msg=in.readUTF();//复制输入流上的内容
					if(msg.equals("serverStop")){
						txtMsg.append("服务器停止\n");
						break;
					}
//					SimpleDateFormat formater=new SimpleDateFormat("HH:mm:ss");//输出实时时间
//					String time=formater.format(new Date());
					txtMsg.append(msg+"\n");//输出到用户界面
					//txtMsg.append(time+"  "+name1+"："+msg+"\n");
				}catch(IOException ex){
					break;
				}
			}
			txtMsg.append("客户下线 \n");
			disconnect();
		}
	}
public static void main(String args[]){
		new Client("fnw");

	}
}
