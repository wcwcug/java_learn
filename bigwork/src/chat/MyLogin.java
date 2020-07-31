package chat;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.*;
import java.util.*;
public class MyLogin extends JFrame implements ActionListener{
	JLabel user=new JLabel("   用户名:");
	JLabel password=new JLabel("      密码:  ");
	JButton login=new JButton("登陆");
	JButton regist=new JButton("注册");
	JTextField text1=new JTextField(18);//用户名文本域
	JTextField text2=new JTextField(18);//用户密码文本域
	String infile2="D:\\userInformation.txt";//创建一个文件保存用户信息
    void infile22(String name,String password){//写文件
    	try {

    		BufferedWriter out=new BufferedWriter(new FileWriter(infile2,true));
    		out.write(name);//写入用户名
    		out.write(password);//写入用户密码
    		out.newLine();//换行
    		out.close();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}}
	String name;//用户名
	int i=1;
	public MyLogin()//构造函数
	{
		/*界面设计*/
		super("聊天室登陆");
		setSize(300,200);
		setVisible(true);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER,15,10));
		add(user);
		add(text1);
		add(password);
		add(text2);
		add(regist);
		add(login);
		login.addActionListener(this);//监听
		regist.addActionListener(this);
		text1.setEditable(true);//允许编辑文本框
		text2.setEditable(true);
		
		/*背景图片*/ 
        setSize(300, 200);          //设置大小 
        setLocation(200, 50);          //设置位置  
        //背景图片的路径。（相对路径或者绝对路径）  
        String path = "D:\\picture.jpg";  
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

	public void actionPerformed(ActionEvent e)//事件触发器，单击鼠标触发
	{
		if(e.getSource()==text1||e.getSource()==regist||e.getSource()==text2){//用户注册
			infile22(text1.getText(),text2.getText());//将用户信息写入文件
		 	name=text1.getText();//得到用户名
			new Client(name);//传参，新建一个客户端类
			System.out.println("登陆界面关闭");
			dispose();//关闭窗口
		}
		else if(e.getSource()==text1||e.getSource()==login||e.getSource()==text2){//用户登陆
			String message=text1.getText()+text2.getText();
	    	String outfile2="D:\\userInformation.txt";//读用户信息文件
	    	try{
	    		BufferedReader in2 =new BufferedReader(new FileReader(outfile2));
	    		String line2;
	    		line2=in2.readLine();//读取一行内容
	    		int flag=0;
	    		while(line2!=null){
	    			if(line2.equals(message))
	    			{
	    				flag=1;
	    				name=text1.getText();//得到用户名
	    				new Client(name);//传参，将用户名传入客户端
	    				System.out.println("登陆界面关闭");
	    				dispose();//关闭窗口
	    			}
	    			line2=in2.readLine();
	    		}
	    		if(flag==0){//重置文本框
	    			JOptionPane.showMessageDialog(null, "密码错误！请重新输入");
	    			text1.setText("");
	    			text1.requestFocus();
	    			text2.setText("");
	    			text2.requestFocus();
	    		}
	    	}catch(Exception ex){
    		
	    	}
	   	}
		
	}

	public static void main(String args[])
	{
		new MyLogin();
	}
}

