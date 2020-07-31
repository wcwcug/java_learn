package cloud;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class matrix extends JFrame implements ActionListener{
	JTextArea txtMsg = new JTextArea();//����һ���µ��ı���
	JTextField txtSendMsg=new JTextField(20);//������һ�����Ϊ 20 ���ַ��ĵ����ı��� 
	JButton btnStop=new JButton("�������");//���������������ҡ���ť
	JButton btnSend1=new JButton("����A");//����һ�����Ͱ�ť
	JButton btnSend2=new JButton("����B");//����һ�����Ͱ�ť
	int n=4;
	String infile1="D:\\A.txt";//����һ���ļ������������ַ��Ϣ
	String infile2="D:\\B.txt";//����һ���ļ������������ַ��Ϣ
    void infile11(String data){//д�ļ��������A

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile1,true));
    		out.write(data);//д��һ�о���ֵ
    		out.newLine();//����
    		out.close();//�ر��ļ�
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    void infile22(String data){//д�ļ��������B

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile2,true));
    		out.write(data);//д��һ�о���ֵ
    		out.newLine();//����
    		out.close();//�ر��ļ�
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	public matrix(){
		Container con =this.getContentPane();//��ʼ��һ������
		txtMsg.setEditable(true);//����༭�ı���
		btnSend1.setEnabled(true);//�������á����롱��ť
		btnSend2.setEnabled(true);//�������á����롱��ť
		btnStop.setEnabled(true);//�������á���ֹ���롱��ť
		txtSendMsg.setEditable(true);//����༭�ı���
		/*����JPanel�����������*/
		JPanel p1=new JPanel();
		JScrollPane p2=new JScrollPane(txtMsg);//�������
		JPanel p3=new JPanel();
		p1.add(btnStop);
		p3.add(txtSendMsg);
		p3.add(btnSend1);
		p3.add(btnSend2);
		con.add(p1, "North");
		con.add(p2, "Center");
		con.add(p3, "South");
		/*����*/
		txtSendMsg.addActionListener(this);
		btnSend1.addActionListener(this);
		btnSend2.addActionListener(this);
		btnStop.addActionListener(this);
		setSize(400,400);
		setTitle("����ֵ");
		setVisible(true);//��ʾ����
		txtMsg.append("���������A�;���B��ֵ\n"+"A,B��Ϊ"+n+"�׾���\n");
	}
	public void actionPerformed(ActionEvent e)//�¼���������������괥��
	{
		if(e.getSource()==btnSend1 ||e.getSource()==txtSendMsg){//�������A���������ı���
			infile11(txtSendMsg.getText());
			txtMsg.append(txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}
		else if(e.getSource()==btnSend2 ||e.getSource()==txtSendMsg){//�������B���������ı���
			infile22(txtSendMsg.getText());
			txtMsg.append(txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}	
		else if(e.getSource()==btnStop){
			dispose();//�رմ���
//			new client();
		}
	}
	public static void main(String args[]){
//		new matrix();
	}
	
}
