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
	static JTextArea users;//������ʾ����ĳ�Ա
   public void init() {  
       try {  
           CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("COM3");  
           // ֱ��ȡ��COM3�˿�  
           System.out.println(portId.getName() + ":����");   
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
               //portId.open("��������������", ��ʱ�ȴ�ʱ��);
           }catch (PortInUseException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}  
 
           try {  
               inputStream = serialPort.getInputStream();  
               //��COM3��ȡ����      
           } catch (IOException e) {  
           }  
 
           try {  
               serialPort.addEventListener(this);  
               //��Ӽ�����  
           } catch (TooManyListenersException e) {  
           }  
 
           serialPort.notifyOnDataAvailable(true);  
           /* 
            * ����������������,���������¼� 
            */  
           try {  
               serialPort.setSerialPortParams(115200,//������  
                       SerialPort.DATABITS_8,//����λ��  
                       SerialPort.STOPBITS_1,//ֹͣλ  
                       SerialPort.PARITY_NONE);//У��  
           } catch (UnsupportedCommOperationException e) {  
           }  
           readThread = new Thread(this);  
          // readThread.start();  
           //�����߳�  
       }  
 
       public void run() {  
           try {  
               Thread.sleep(2000);  
               serialPort.close();  
               System.out.println("COM1:�ر�");  
               //�趨30���˿ڹرգ�������֮����  
           } catch (InterruptedException e) { 
           }  
       }  
 
       /** 
        * BI -ͨѶ�ж�. CD -�ز����. CTS -�������. DATA_AVAILABLE -�����ݵ���. DSR -�����豸׼����. 
        * FE -֡����. OE -��λ����. OUTPUT_BUFFER_EMPTY -��������������. PE -��żУ���. RI - 
        * ����ָʾ. һ����õľ���DATA_AVAILABLE--���������ݵ����¼��� 
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
                		System.out.println("���������ɹ���");
                	}catch(Exception e){
                		e.printStackTrace();
                		System.out.println("��������ʧ�ܣ�");
                	}
                	try{
                		dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
                			System.out.println("�������ݿ�ɹ���");
                	}catch(Exception e)
                	{
                		e.printStackTrace();
                		System.out.print("SQL Server����ʧ�ܣ�");
                	}
                	
                	/*�任������ɫ*/
                	JFrame.setDefaultLookAndFeelDecorated(true);
                	JDialog.setDefaultLookAndFeelDecorated(true);
                	try
                	{
                	    UIManager.setLookAndFeel(UIManager
                	            .getCrossPlatformLookAndFeelClassName());
                	    UIManager
                	            .setLookAndFeel(new SubstanceChallengerDeepLookAndFeel());//����Ƥ��
                	    

                	}
                	catch (Exception e)
                	{
                	    e.printStackTrace();
                	}
                	/*�����´���*/
                	JFrame frame=new JFrame("RFID���ܽ��ҿ���ϵͳ");//��һ���µĴ���
                	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ùرմ������˳�����
                	rfid cc = new rfid();
                	JPanel content=cc.createComponents();
                	frame.getContentPane().add(content);
                	frame.setSize(550, 500);//�趨���ڳߴ�
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
                                   System.out.println("���Դ�����:" + temp);
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
                       //���������ַ�  
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
	 //���ڲ���
	 JPanel pane=new JPanel(new BorderLayout());//����һ��ָ�����ֵ�Panel
	 JPanel listPanel=new JPanel();//����һ�������б�
	 users=new JTextArea(100,100);//����10��20���ı���
	 JScrollPane scrollPane2 = new JScrollPane(users);
	 listPanel.setBorder(BorderFactory.createTitledBorder("�������"));
	 listPanel.add(users);//������߷���������
	 pane.add(listPanel,"North");//�ֲ�����λ��
	 return pane;

	 }

 
   public static void main(String[] args) {  
       rfid reader = new rfid();  
       reader.init();  
       reader.retValue = "0";  
       //(reader.retValue);  
   }  
} 
