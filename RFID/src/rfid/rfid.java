package rfid;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;  
import java.util.List;  
import java.util.TooManyListenersException;
import java.awt.BorderLayout;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;

import gnu.io.*;    
 
public class rfid {  
	 int flag1=0;
    static String retValue = "000000";  
	static Connection dbConn=null ;
	static JTextArea users;//定义显示界面的成员
   public void init() {  
       try {  
           CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("COM3");  
           // 直接取得COM3端口  
           System.out.println(portId.getName() + ":开启");   
           @SuppressWarnings("unused")  
           Read reader = new Read(portId);  
       } catch (Exception ex) {  
           ex.printStackTrace();
       }  
   }  
 
   class Read implements Runnable, SerialPortEventListener {  
 
       InputStream inputStream;  
       SerialPort serialPort;  
       Thread readThread;  
 
       public Read(CommPortIdentifier portId) throws InterruptedException {  
           try {  
               serialPort = (SerialPort) portId.open("123",2000);  
               //portId.open("串口所有者名称", 超时等待时间);
           }catch (PortInUseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
 
           try {  
               inputStream = serialPort.getInputStream();  
               //从COM3获取数据      
           } catch (IOException e) {  
           }  
 
           try {  
               serialPort.addEventListener(this);  
               //添加监听器  
           } catch (TooManyListenersException e) {  
           }  
 
           serialPort.notifyOnDataAvailable(true);  
           /* 
            * 侦听到串口有数据,触发串口事件 
            */  
           try {  
               serialPort.setSerialPortParams(115200,//波特率  
                       SerialPort.DATABITS_8,//数据位数  
                       SerialPort.STOPBITS_1,//停止位  
                       SerialPort.PARITY_NONE);//校验  
           } catch (UnsupportedCommOperationException e) {  
           }  
           readThread = new Thread(this);  
          // readThread.start();  
           //启动线程  
       }  
 
       public void run() {  
           try {  
               Thread.sleep(2000);  
               serialPort.close();  
               System.out.println("COM1:关闭");  
               //设定30秒后端口关闭，程序随之结束  
           } catch (InterruptedException e) { 
           }  
       }  
 
       /** 
        * BI -通讯中断. CD -载波检测. CTS -清除发送. DATA_AVAILABLE -有数据到达. DSR -数据设备准备好. 
        * FE -帧错误. OE -溢位错误. OUTPUT_BUFFER_EMPTY -输出缓冲区已清空. PE -奇偶校验错. RI - 
        * 振铃指示. 一般最常用的就是DATA_AVAILABLE--串口有数据到达事件。 
        */  
       public void serialEvent(SerialPortEvent event) {  
 
           switch (event.getEventType()) {  
               case SerialPortEvent.BI:  
               case SerialPortEvent.OE:  
               case SerialPortEvent.FE:  
               case SerialPortEvent.PE:  
               case SerialPortEvent.CD:  
               case SerialPortEvent.CTS:  
               case SerialPortEvent.DSR:  
               case SerialPortEvent.RI:  
               case SerialPortEvent.OUTPUT_BUFFER_EMPTY:  
                   break;  
               case SerialPortEvent.DATA_AVAILABLE:  
                   byte[] readBuffer = new byte[20];  
 
                   try {  
               	 
                	 String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
                	  String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=Test";
                	  String userName="sa";
                	  String userPwd="wangchenwei";
                	 try
                	{
                		Class.forName(driverName);
                		System.out.println("加载驱动成功！");
                	}catch(Exception e){
                		e.printStackTrace();
                		System.out.println("加载驱动失败！");
                	}
                	try{
                		dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
                			System.out.println("连接数据库成功！");
                	}catch(Exception e)
                	{
                		e.printStackTrace();
                		System.out.print("SQL Server连接失败！");
                	}
                	
                	/*变换界面颜色*/
                	JFrame.setDefaultLookAndFeelDecorated(true);
                	JDialog.setDefaultLookAndFeelDecorated(true);
                	try
                	{
                	    UIManager.setLookAndFeel(UIManager
                	            .getCrossPlatformLookAndFeelClassName());
                	    UIManager
                	            .setLookAndFeel(new SubstanceChallengerDeepLookAndFeel());//更换皮肤
                	    

                	}
                	catch (Exception e)
                	{
                	    e.printStackTrace();
                	}
                	/*创建新窗口*/
                	JFrame frame=new JFrame("RFID智能教室考勤系统");//建一个新的窗口
                	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗口则退出程序
                	rfid cc = new rfid();
                	JPanel content=cc.createComponents();
                	frame.getContentPane().add(content);
                	frame.setSize(550, 500);//设定窗口尺寸
                	frame.setVisible(true);

                	
                       while (inputStream.available() > 0) {  

                    	   flag1++;
                           BufferedReader comReader = new BufferedReader(new InputStreamReader(inputStream));  
                           String temp = comReader.readLine().trim();  
                           System.out.println(temp);  

                           print d=new print();

                           d.put(temp,flag1);
               
                           if (temp.indexOf("#0") >= 0 || temp.indexOf("#8") >= 0) {  
                               int p = temp.indexOf("#");  
                               temp = temp.substring(p + 3, temp.length()).replace(" ", "").trim();  
                               try {  
                                   Integer.parseInt(temp);  
                               } catch (Exception e) {  
                                   System.out.println("忽略次数据:" + temp);
                                   retValue = "0";  
                                   return;  
                               }  
                               System.out.println(temp);

                               retValue = String.valueOf(Integer.parseInt(temp.substring(1, 5))) + "." + temp.substring(5, 6);  
                               System.out.println(retValue);  
                           }  
                       }  
                       String strWGT = new String(readBuffer).trim();  
                       System.out.println(strWGT);  
                       //输出读入的字符  
                   } catch (IOException e) {
                   } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
                   break;  
           }  
       }  
   }  
   public JPanel createComponents(){
	 //窗口布局
	 JPanel pane=new JPanel(new BorderLayout());//创建一个指定布局的Panel
	 JPanel listPanel=new JPanel();//定义一个在线列表
	 users=new JTextArea(100,100);//设置10，20的文本区
	 JScrollPane scrollPane2 = new JScrollPane(users);
	 listPanel.setBorder(BorderFactory.createTitledBorder("到课情况"));
	 listPanel.add(users);//添加在线服务器界面
	 pane.add(listPanel,"North");//分布窗口位置
	 return pane;

	 }

 
   public static void main(String[] args) {  
       rfid reader = new rfid();  
       reader.init();  
       reader.retValue = "0";  
       //(reader.retValue);  
   }  
} 
