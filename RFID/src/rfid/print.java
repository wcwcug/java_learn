package rfid;
import java.util.*;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;
public class print {
	
    rfid conn=new rfid();
   
public void put(String num,int flag1) throws SQLException{ 
    //System.out.println("��ɹ�");
	int flag=flag1;
    ResultSet rs=null;  
    Statement sm=null; 
//    Connection db=null ;
//    Statement stmt = db.createStatement();// ����SQL�������
    String q=null;

    //��ȡ����
    sm=conn.dbConn.createStatement();
   //System.out.println("�����ɹ�");
    rs=sm.executeQuery("select num,name,flag  from Table_1"); 
  //ѭ��ȡ��  
    int i=0;
    while(rs.next()){  
        String a=rs.getString(1);
        String c=rs.getString(2);
        int b=rs.getInt(3);          
        System.out.println("*"+a+"*"+b+"*"+c+"*");
        

        if(/*a.equals(num)&&b==0*/flag %2==1)
        {
        	
        	conn.users.append("\n");
        	conn.users.append("\n");
        	conn.users.append("������ҵ�ʱ�� ��"+time()+"\n");
        	conn.users.append("ѧ�� ��"+a+"\n");
        	conn.users.append("���� ��"+c+"\n");
        	conn.users.append("״̬  ������\n");
        	
        	  	
        	
        }
        if(/*a.equals(num)&&b==1*/flag %2==0)
        {
        	conn.users.append("\n");
        	conn.users.append("\n");
        	conn.users.append("�뿪���ҵ�ʱ�� ��"+time()+"\n");
        	conn.users.append("ѧ�� ��"+a+"\n");
        	conn.users.append("���� ��"+c+"\n");
        	conn.users.append("״̬  ���뿪\n");
 	
        	
        }
        i++;
        q=c;
    } 
//    turn e=new turn();
//    e.insert(num,q,time());
    System.out.println("��ʼ��������");
    String a1 =String.format( "INSERT INTO s2014  VALUES('%s','%s','%s')",num,q,time());// ��������SQL���
    sm.executeUpdate(a1);// ִ��SQL�������
    System.out.println("�������ݳɹ�");//}
}

//��ȡϵͳʱ��
public String time()
{
	Date date= new Date();//����һ��ʱ����󣬻�ȡ����ǰ��ʱ��
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");//����ʱ����ʾ��ʽ
	String str = sdf.format(date);//����ǰʱ���ʽ��Ϊ��Ҫ������
	return str;
}

}

