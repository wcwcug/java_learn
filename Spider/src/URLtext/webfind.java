package URLtext;
import java.net.*;
import java.io.*;
public class webfind {
	 public static void main(String args[]) throws Exception
	 {
	  try
	  {
		  String url = "http://dkxy.cug.edu.cn/jsfc/yydqwlx.htm";//www.baidu.com";
		  URL theUrl= new URL(url);
		  InputStream openStream = theUrl.openStream();
		  //<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		  //�����������ĵ��ַ��������HTMLԴ���е� charsetһ��
		  BufferedReader bf = new BufferedReader(new InputStreamReader(openStream,"utf-8"));
		  String line = null;
		  while((line = bf.readLine())!=null) {
		      System.out.println(line);
		  }
	  }
	   catch(Exception e)
	   {
	    System.out.println(e);
	   }
	 }
}
