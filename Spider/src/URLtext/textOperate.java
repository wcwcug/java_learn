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
   log("本机地址字符串:"+address.getHostAddress());
   log("本机主机名:"+address.getHostName());
   log("本机主机名:"+address.getLocalHost());
   log("哈希码:"+address.hashCode());
   byte b[]=address.getAddress();
   System.out.println("字符形式:"+b);
   log("地址字符串:"+address.toString());
  }
  catch(Exception e)
  {
   //e.printStackTrace("不能打开这个URL");
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
