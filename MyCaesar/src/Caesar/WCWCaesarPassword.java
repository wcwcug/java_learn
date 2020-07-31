package Caesar;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;

public class WCWCaesarPassword extends JFrame implements ActionListener{
	JLabel input1=new JLabel("         原文:  ");
	JLabel key1=new JLabel("         密钥:  ");
	JLabel output1=new JLabel("加密输出:  ");
	JLabel input2=new JLabel("         密文:  ");
	JLabel key2=new JLabel("暴力破解密钥:");
	JLabel output2=new JLabel("解密输出:  ");
	JButton encryption=new JButton("加密");
	JButton deciphering=new JButton("解密");
	JButton exit=new JButton("退出");
	JTextField text1=new JTextField(25);//加密文本域
	JTextField text2=new JTextField(25);//密钥文本域
	JTextField text3=new JTextField(25);//加密输出文本域
	JTextField text4=new JTextField(25);//解密文本域
	JTextField text5=new JTextField(23);//暴力破解密钥文本域
	JTextField text6=new JTextField(25);//解密输出文本域
	int flag=0;//标记位
	public WCWCaesarPassword()//构造函数
	{
		/*界面设计*/
		super("凯撒密码加解密（王晨威）");
		setSize(400,300);
		setVisible(true);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER,15,10));
		add(input1);
		add(text1);
		add(key1);
		add(text2);
		add(output1);
		add(text3);
		add(input2);
		add(text4);
		add(key2);
		add(text5);
		add(output2);
		add(text6);
		add(encryption);
		add(deciphering);
		add(exit);
		encryption.addActionListener(this);//监听
		deciphering.addActionListener(this);
		exit.addActionListener(this);
		text1.setEditable(true);//允许编辑文本框
		text2.setEditable(true);
		text3.setEditable(true);
		text4.setEditable(true);
		text5.setEditable(true);
		text6.setEditable(true);
		
		/*背景图片*/ 
        setSize(400, 300);          //设置大小 
        setLocation(200, 50);          //设置位置  
        //背景图片的路径。（相对路径或者绝对路径）  
        String path = "D:\\image.png";  
        ImageIcon background = new ImageIcon(path);          // 背景图片  
        JLabel label = new JLabel(background);          // 把背景图片显示在一个标签里面  
        label.setBounds(0, 0, this.getWidth(), this.getHeight());          // 把标签的大小位置设置为图片刚好填充整个面板
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
        JPanel imagePanel = (JPanel) this.getContentPane();  
        imagePanel.setOpaque(false);  
        // 把背景图片添加到分层窗格的最底层作为背景  
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
        //设置可见  
        setVisible(true);  
        //点关闭按钮时退出  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//获取界面尺寸对象
		setLocation((screen.width-300)/2,(screen.height-220)/2);
		
		addWindowListener(new WindowAdapter()//frame追加一个windows事件监听
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();//关闭窗体释放该窗体的占用的屏幕资源
				System.exit(0);
			}
		});
	}

	String encrypt(String a,int key) //加密算法
	{
		char[] bm;
		bm = a.toCharArray();//转换为字符数组
		int temp = 0; 
		for (int i = 0; i < bm.length; i++) {  
			temp = bm[i] + key; 
            if (bm[i] == ' ' || bm[i] == ',' || bm[i] == '.' || bm[i] == '!') {  
            	bm[i] = (char) (bm[i]);  
             }  
             // ASCII码 A=65,Z=90; a=97 z=122;  
            if (bm[i] >= 'a' && bm[i] <= 'z') {  
                if (temp > 122)  
                	bm[i] = (char) (97 + temp - 123);  
                 else {  
                	 bm[i] = (char) temp;  
                 }  
             }  
             if (bm[i] >= 'A' && bm[i] <= 'Z') {  
                 if ((bm[i] + key) > 90)  
                	 bm[i] = (char) (65 + temp - 91);  
                 else {  
                	 bm[i] = (char) temp;  
                 }  
             }  
         } 
		String b;
		b = String.valueOf(bm); 
		return b;	 
	}
	String deciphering(String b,int key) //解密算法
	{
		char[] cm;
		cm = b.toCharArray();//转换为字符数组 
		int temp=0;
	    for (int i = 0; i < cm.length; i++) {  
	           temp = cm[i] - key;  
	           if (cm[i] == ' ' || cm[i] == ',' || cm[i] == '.' || cm[i] == '!') {  
	        	   cm[i] = (char) (cm[i]);  
	               }  
	           if (cm[i] >= 97 && cm[i] <= 122) {  
	        	   cm[i] = (char) (temp);  
	                   if (temp < 97) {  
	                	   cm[i] = (char) (26 + temp);  
	                    }  
	                }  
	                if (cm[i] >= 65 && cm[i] <= 90) {  
	                	cm[i]= (char) (temp);  
	                    if (temp < 65) {  
	                    	cm[i]= (char) (26 + temp);  
	                    }  
	                }      
	            }  
		String bb;
		bb = String.valueOf(cm); 
		return bb;		         
	}
    int compare(String word)    //判断是否为单词
    {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
		try {
			String str = "";
			fis = new FileInputStream("E:\\物联网安全技术\\EnglishWords.txt");// FileInputStream
			// 从文件系统中的某个文件中获取字节
			isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
			while ((str = br.readLine()) != null) {				
				if(str.equals(word))
				{
					//System.out.println("单词正确");
					return 0;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取文件失败");
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
				// 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
    }
	public void actionPerformed(ActionEvent e)//事件触发器，单击鼠标触发
	{
		if(e.getSource()==text1||e.getSource()==encryption||e.getSource()==text2){//用户加密
			int key=0;
			try { //获取密钥
			    key = Integer.parseInt(text2.getText()); 
			    key = key % 26;
				text3.setText(encrypt(text1.getText(),key));
			} catch (NumberFormatException ee) { 
				System.out.println("Error!!!请重新输入整数密钥...");  
			    ee.printStackTrace(); 
			}

		}
		else if(e.getSource()==text4||e.getSource()==deciphering){//用户解密(暴力破解）
			
	    	try{
    			int error=0;//标记位
	    		for(int key=1;key<26;key++){
	    			flag=0;
	    			String deci=deciphering(text4.getText(),key);
	    			String[] Words = deci.split(" ");//切分
			        for (int i = 0; i < Words.length; i++) 
			        {
			        	flag+=compare(Words[i]);
			        }
			        if(flag==0)
			        {
			        	System.out.println("输出皆为单词！");
			        	error=1;
			        	text6.setText(deci);
			        	text5.setText(String.valueOf(key));
			        	break;
			        }
	    		}
	    		if(error==0)
	    		{
	    			System.out.println("输出存在非单词！");
	    			text5.setText("Null");
	    			text6.setText("破解为非英语句子！！！请确定密文……");
	    		}

	    	}catch(NumberFormatException ee){
				System.out.println("Error!!!请重新输入整数密钥...");  
			    ee.printStackTrace(); 
	    	}
	   	}
		else if(e.getSource()==exit)//关闭
		{
			System.out.println("登陆界面关闭");
			dispose();//关闭窗口
		}
		
	}

	public static void main(String args[])
	{
		new WCWCaesarPassword();
	}
}

