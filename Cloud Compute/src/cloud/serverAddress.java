package cloud;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
public class serverAddress extends JFrame implements ActionListener{//�������������Ϣ
	JTextArea txtMsg = new JTextArea();//����һ���µ��ı���
	JTextField txtSendMsg=new JTextField(20);//������һ�����Ϊ 20 ���ַ��ĵ����ı��� 
	JButton btnStop=new JButton("�������");//���������������ҡ���ť
	JButton btnSend=new JButton("����");//����һ�����Ͱ�ť
	int flag=0;
	String infile1="D:\\node.txt";//����һ���ļ������������ַ��Ϣ
    void infile(String data){//д�ļ������������ַ��Ϣ

    	try {
    		BufferedWriter out=new BufferedWriter(new FileWriter(infile1,true));
    		out.write(data);//д���������ַ
    		out.newLine();//����
    		out.close();//�ر��ļ�
    		flag++;//������������1
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	public serverAddress(){
		Container con =this.getContentPane();//��ʼ��һ������
		txtMsg.setEditable(true);//����༭�ı���
		btnSend.setEnabled(true);//�������á����롱��ť
		btnStop.setEnabled(true);//�������á�������ϡ���ť
		txtSendMsg.setEditable(true);//����༭�ı���
		/*����JPanel�����������*/
		JPanel p1=new JPanel();
		JScrollPane p2=new JScrollPane(txtMsg);//�������
		JPanel p3=new JPanel();
		p1.add(btnStop);
		p3.add(txtSendMsg);
		p3.add(btnSend);
		con.add(p1, "North");
		con.add(p2, "Center");
		con.add(p3, "South");
		/*����*/
		txtSendMsg.addActionListener(this);
		btnSend.addActionListener(this);
		btnStop.addActionListener(this);
		setSize(400,400);
		setTitle("�������˵�ַ");
		setVisible(true);//��ʾ����
	}
	public void actionPerformed(ActionEvent e)//�¼���������������괥��
	{
		if(e.getSource()==btnSend ||e.getSource()==txtSendMsg){//���������IP���������ı���
			infile(txtSendMsg.getText());
			txtMsg.append("������IP��ַ��"+txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}else if(e.getSource()==btnStop){
			dispose();//�رմ���
			new matrix();
		}
	}
	public static void main(String args[]){
		new serverAddress();
	}
}

