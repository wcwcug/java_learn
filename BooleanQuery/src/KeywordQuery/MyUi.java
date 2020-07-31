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
public class MyUi extends JFrame implements ActionListener{
	JLabel words=new JLabel("  �ؼ���:");
	JLabel text=new JLabel("��Ӧ�ĵ�:");
	JButton search=new JButton("����");
	JTextField text1=new JTextField(25);//�ؼ����ı���
	JTextArea text2 = new JTextArea(10,25);//��Ӧ�ĵ��ı���
	//JTextField text2=new JTextField(18);//��Ӧ�ĵ��ı���
	public MyUi()//���캯��
	{
		/*�������*/
		super("������ĵ��ż�¼��-������");
		setSize(300,300);
		setVisible(true);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER,20,15));
		add(words);
		add(text1);
		add(text);
		add(text2);
		add(search);
		search.addActionListener(this);//����
		text1.setEditable(true);//����༭�ı���
		text2.setEditable(true);
		text2.setLineWrap(true);//�����ı����Ļ��в��ԡ�
			
		/*����ͼƬ*/ 
	    setSize(400, 350);          //���ô�С 
	    setLocation(200, 50);          //����λ��  
        //����ͼƬ��·���������·�����߾���·����  
        String path = "D:\\image.jpg";  
        ImageIcon background = new ImageIcon(path);          // ����ͼƬ  
	    JLabel label = new JLabel(background);          // �ѱ���ͼƬ��ʾ��һ����ǩ����  
        label.setBounds(0, 0, this.getWidth(), this.getHeight());          // �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������
        // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��  
	    JPanel imagePanel = (JPanel) this.getContentPane();  
        imagePanel.setOpaque(false);  
        // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����  
	    this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
        //���ÿɼ�  
	    setVisible(true);  
	    //��رհ�ťʱ�˳�  
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//��ȡ����ߴ����
		setLocation((screen.width-300)/2,(screen.height-220)/2);
			
		addWindowListener(new WindowAdapter()//frame׷��һ��windows�¼�����
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();//�رմ����ͷŸô����ռ�õ���Ļ��Դ
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e)//�¼���������������괥��
	{
		 try{
		 if(e.getSource()==text1||e.getSource()==search){//�û���ѯ
			String message=text1.getText();
			String[]txt=message.split("[^A-Za-z]+");//�Է���ĸ������Ϊ����ƾ��
			if(txt.length==1)//���ؼ��ֲ�ѯ
			{
				TreeMap<String,ArrayList> map = new TreeMap<String, ArrayList>();
				InvertedIndex a=new InvertedIndex();
				map=a.Create();
				String t=a.JumpTableMethod1(map,txt[0]);
				text2.setText(t);
			}
			else if(txt.length>1)//��ؼ��ֲ�ѯ
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
				String t=a.JumpTableMethod2(map,words);
				text2.setText(t);
			}

		}
		}catch(Exception ex)
		{
			 
		}
			
	}

	public static void main(String args[])
	{
		new MyUi();
	}
		
}



