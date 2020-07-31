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
	JLabel input1=new JLabel("         ԭ��:  ");
	JLabel key1=new JLabel("         ��Կ:  ");
	JLabel output1=new JLabel("�������:  ");
	JLabel input2=new JLabel("         ����:  ");
	JLabel key2=new JLabel("�����ƽ���Կ:");
	JLabel output2=new JLabel("�������:  ");
	JButton encryption=new JButton("����");
	JButton deciphering=new JButton("����");
	JButton exit=new JButton("�˳�");
	JTextField text1=new JTextField(25);//�����ı���
	JTextField text2=new JTextField(25);//��Կ�ı���
	JTextField text3=new JTextField(25);//��������ı���
	JTextField text4=new JTextField(25);//�����ı���
	JTextField text5=new JTextField(23);//�����ƽ���Կ�ı���
	JTextField text6=new JTextField(25);//��������ı���
	int flag=0;//���λ
	public WCWCaesarPassword()//���캯��
	{
		/*�������*/
		super("��������ӽ��ܣ���������");
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
		encryption.addActionListener(this);//����
		deciphering.addActionListener(this);
		exit.addActionListener(this);
		text1.setEditable(true);//����༭�ı���
		text2.setEditable(true);
		text3.setEditable(true);
		text4.setEditable(true);
		text5.setEditable(true);
		text6.setEditable(true);
		
		/*����ͼƬ*/ 
        setSize(400, 300);          //���ô�С 
        setLocation(200, 50);          //����λ��  
        //����ͼƬ��·���������·�����߾���·����  
        String path = "D:\\image.png";  
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

	String encrypt(String a,int key) //�����㷨
	{
		char[] bm;
		bm = a.toCharArray();//ת��Ϊ�ַ�����
		int temp = 0; 
		for (int i = 0; i < bm.length; i++) {  
			temp = bm[i] + key; 
            if (bm[i] == ' ' || bm[i] == ',' || bm[i] == '.' || bm[i] == '!') {  
            	bm[i] = (char) (bm[i]);  
             }  
             // ASCII�� A=65,Z=90; a=97 z=122;  
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
	String deciphering(String b,int key) //�����㷨
	{
		char[] cm;
		cm = b.toCharArray();//ת��Ϊ�ַ����� 
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
    int compare(String word)    //�ж��Ƿ�Ϊ����
    {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; //���ڰ�װInputStreamReader,��ߴ������ܡ���ΪBufferedReader�л���ģ���InputStreamReaderû�С�
		try {
			String str = "";
			fis = new FileInputStream("E:\\��������ȫ����\\EnglishWords.txt");// FileInputStream
			// ���ļ�ϵͳ�е�ĳ���ļ��л�ȡ�ֽ�
			isr = new InputStreamReader(fis);// InputStreamReader ���ֽ���ͨ���ַ���������,
			br = new BufferedReader(isr);// ���ַ��������ж�ȡ�ļ��е�����,��װ��һ��new InputStreamReader�Ķ���
			while ((str = br.readLine()) != null) {				
				if(str.equals(word))
				{
					//System.out.println("������ȷ");
					return 0;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("�Ҳ���ָ���ļ�");
		} catch (IOException e) {
			System.out.println("��ȡ�ļ�ʧ��");
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
				// �رյ�ʱ����ð����Ⱥ�˳��ر���󿪵��ȹر������ȹ�s,�ٹ�n,����m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
    }
	public void actionPerformed(ActionEvent e)//�¼���������������괥��
	{
		if(e.getSource()==text1||e.getSource()==encryption||e.getSource()==text2){//�û�����
			int key=0;
			try { //��ȡ��Կ
			    key = Integer.parseInt(text2.getText()); 
			    key = key % 26;
				text3.setText(encrypt(text1.getText(),key));
			} catch (NumberFormatException ee) { 
				System.out.println("Error!!!����������������Կ...");  
			    ee.printStackTrace(); 
			}

		}
		else if(e.getSource()==text4||e.getSource()==deciphering){//�û�����(�����ƽ⣩
			
	    	try{
    			int error=0;//���λ
	    		for(int key=1;key<26;key++){
	    			flag=0;
	    			String deci=deciphering(text4.getText(),key);
	    			String[] Words = deci.split(" ");//�з�
			        for (int i = 0; i < Words.length; i++) 
			        {
			        	flag+=compare(Words[i]);
			        }
			        if(flag==0)
			        {
			        	System.out.println("�����Ϊ���ʣ�");
			        	error=1;
			        	text6.setText(deci);
			        	text5.setText(String.valueOf(key));
			        	break;
			        }
	    		}
	    		if(error==0)
	    		{
	    			System.out.println("������ڷǵ��ʣ�");
	    			text5.setText("Null");
	    			text6.setText("�ƽ�Ϊ��Ӣ����ӣ�������ȷ�����ġ���");
	    		}

	    	}catch(NumberFormatException ee){
				System.out.println("Error!!!����������������Կ...");  
			    ee.printStackTrace(); 
	    	}
	   	}
		else if(e.getSource()==exit)//�ر�
		{
			System.out.println("��½����ر�");
			dispose();//�رմ���
		}
		
	}

	public static void main(String args[])
	{
		new WCWCaesarPassword();
	}
}

