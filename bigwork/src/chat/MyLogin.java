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
	JLabel user=new JLabel("   �û���:");
	JLabel password=new JLabel("      ����:  ");
	JButton login=new JButton("��½");
	JButton regist=new JButton("ע��");
	JTextField text1=new JTextField(18);//�û����ı���
	JTextField text2=new JTextField(18);//�û������ı���
	String infile2="D:\\userInformation.txt";//����һ���ļ������û���Ϣ
    void infile22(String name,String password){//д�ļ�
    	try {

    		BufferedWriter out=new BufferedWriter(new FileWriter(infile2,true));
    		out.write(name);//д���û���
    		out.write(password);//д���û�����
    		out.newLine();//����
    		out.close();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}}
	String name;//�û���
	int i=1;
	public MyLogin()//���캯��
	{
		/*�������*/
		super("�����ҵ�½");
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
		login.addActionListener(this);//����
		regist.addActionListener(this);
		text1.setEditable(true);//����༭�ı���
		text2.setEditable(true);
		
		/*����ͼƬ*/ 
        setSize(300, 200);          //���ô�С 
        setLocation(200, 50);          //����λ��  
        //����ͼƬ��·���������·�����߾���·����  
        String path = "D:\\picture.jpg";  
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
		if(e.getSource()==text1||e.getSource()==regist||e.getSource()==text2){//�û�ע��
			infile22(text1.getText(),text2.getText());//���û���Ϣд���ļ�
		 	name=text1.getText();//�õ��û���
			new Client(name);//���Σ��½�һ���ͻ�����
			System.out.println("��½����ر�");
			dispose();//�رմ���
		}
		else if(e.getSource()==text1||e.getSource()==login||e.getSource()==text2){//�û���½
			String message=text1.getText()+text2.getText();
	    	String outfile2="D:\\userInformation.txt";//���û���Ϣ�ļ�
	    	try{
	    		BufferedReader in2 =new BufferedReader(new FileReader(outfile2));
	    		String line2;
	    		line2=in2.readLine();//��ȡһ������
	    		int flag=0;
	    		while(line2!=null){
	    			if(line2.equals(message))
	    			{
	    				flag=1;
	    				name=text1.getText();//�õ��û���
	    				new Client(name);//���Σ����û�������ͻ���
	    				System.out.println("��½����ر�");
	    				dispose();//�رմ���
	    			}
	    			line2=in2.readLine();
	    		}
	    		if(flag==0){//�����ı���
	    			JOptionPane.showMessageDialog(null, "�����������������");
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

