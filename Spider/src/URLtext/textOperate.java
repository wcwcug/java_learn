package URLtext;
import java.net.*;
import java.io.*;
public class textOperate//extends Applet
{
 public void textOperate()
 {
  try
  {
   InetAddress address=InetAddress.getLocalHost();
   log("������ַ�ַ���:"+address.getHostAddress());
   log("����������:"+address.getHostName());
   log("����������:"+address.getLocalHost());
   log("��ϣ��:"+address.hashCode());
   byte b[]=address.getAddress();
   System.out.println("�ַ���ʽ:"+b);
   log("��ַ�ַ���:"+address.toString());
  }
  catch(Exception e)
  {
   //e.printStackTrace("���ܴ����URL");
  }
 }
 
 public void log(String strInfo)
 {
  System.out.println(strInfo);
 }
 
 public static void main(String args[])
 {
	 textOperate IAdd=new textOperate();
  IAdd.textOperate();
 }
}
