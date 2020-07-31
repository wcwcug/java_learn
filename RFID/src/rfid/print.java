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
    //System.out.println("表成功");
	int flag=flag1;
    ResultSet rs=null;  
    Statement sm=null; 
//    Connection db=null ;
//    Statement stmt = db.createStatement();// 创建SQL命令对象
    String q=null;

    //读取数据
    sm=conn.dbConn.createStatement();
   //System.out.println("表创建成功");
    rs=sm.executeQuery("select num,name,flag  from Table_1"); 
  //循环取出  
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
        	conn.users.append("进入教室的时间 ："+time()+"\n");
        	conn.users.append("学号 ："+a+"\n");
        	conn.users.append("姓名 ："+c+"\n");
        	conn.users.append("状态  ：进入\n");
        	
        	  	
        	
        }
        if(/*a.equals(num)&&b==1*/flag %2==0)
        {
        	conn.users.append("\n");
        	conn.users.append("\n");
        	conn.users.append("离开教室的时间 ："+time()+"\n");
        	conn.users.append("学号 ："+a+"\n");
        	conn.users.append("姓名 ："+c+"\n");
        	conn.users.append("状态  ：离开\n");
 	
        	
        }
        i++;
        q=c;
    } 
//    turn e=new turn();
//    e.insert(num,q,time());
    System.out.println("开始插入数据");
    String a1 =String.format( "INSERT INTO s2014  VALUES('%s','%s','%s')",num,q,time());// 插入数据SQL语句
    sm.executeUpdate(a1);// 执行SQL命令对象
    System.out.println("插入数据成功");//}
}

//获取系统时间
public String time()
{
	Date date= new Date();//创建一个时间对象，获取到当前的时间
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");//设置时间显示格式
	String str = sdf.format(date);//将当前时间格式化为需要的类型
	return str;
}

}

