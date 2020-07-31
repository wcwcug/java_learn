package KeywordQuery;

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
public class MyUi2 extends JFrame implements ActionListener{
	JLabel words=new JLabel("  关键字:");
	JLabel text=new JLabel("对应文档:");
	JButton search=new JButton("索引");
	JTextField text1=new JTextField(25);//关键字文本域
	JTextArea text2 = new JTextArea(10,25);//对应文档文本域
	//JTextField text2=new JTextField(18);//对应文档文本域
	public MyUi2()//构造函数
	{
		/*界面设计*/
		super("多关键字的布尔查询-王晨威");
		setSize(300,300);
		setVisible(true);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER,20,15));
		add(words);
		add(text1);
		add(text);
		add(text2);
		add(search);
		search.addActionListener(this);//监听
		text1.setEditable(true);//允许编辑文本框
		text2.setEditable(true);
		text2.setLineWrap(true);//设置文本区的换行策略。
			
		/*背景图片*/ 
	    setSize(400, 350);          //设置大小 
	    setLocation(200, 50);          //设置位置  
        //背景图片的路径。（相对路径或者绝对路径）  
        String path = "D:\\image.jpg";  
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
		 try{
		 if(e.getSource()==text1||e.getSource()==search){//用户查询
			String message=text1.getText();
			String[]txt=message.split("[^A-Za-z]+");//以非字母符号作为划分凭据
			if(txt.length==1)//单关键字查询
			{
				TreeMap<String,ArrayList> map = new TreeMap<String, ArrayList>();
				InvertedIndex a=new InvertedIndex();
				map=a.Create();
				String t=a.JumpTableMethod1(map,txt[0]);
				text2.setText(t);
			}
			else if(txt.length>1)//多关键字查询
			{
				String words=new String();
				for(int i=0;i<txt.length;i++)
				{
					words+=txt[i];
					words+=" ";
				}
				TreeMap<String,ArrayList> map = new TreeMap<String, ArrayList>();
				InvertedIndex a=new InvertedIndex();
				map=a.Create();
				String t=a.JumpTableMethod3(map,words);
				text2.setText(t);
			}

		}
		}catch(Exception ex)
		{
			 
		}
			
	}

	public static void main(String args[])
	{
		new MyUi2();
	}
		
}




