package rfid;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
public class find extends JFrame implements ActionListener{//��ѯѧ��������Ϣ
	JTextArea txtMsg = new JTextArea();//����һ���µ��ı���
	JTextField txtSendMsg=new JTextField(20);//������һ�����Ϊ 20 ���ַ��ĵ����ı��� 
	JButton btnStop=new JButton("�˳���ѯ");//����һ���˳����水ť
	JButton btnSend=new JButton("����ѧ��ѧ��");//���������롱��ť
	int flag=0;

	void link(String num){//���ݿ⽨������
		System.out.println("���������ɹ���");
		 String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		  String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=Test";
		  String userName="sa";
		  String userPwd="wangchenwei";
        try
        {
        	Class.forName(driverName);
        	System.out.println("���������ɹ���");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println("��������ʧ�ܣ�");
        }
        try{
    	Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);//�����������ݿ����
		System.out.println("�������ݿ�ɹ���");
		Statement st = dbConn.createStatement();//����SQL�������
		ResultSet rs=st.executeQuery("SELECT * FROM s"+num);//��������ѧ����Ϣ��
		System.out.println("ѧ��������Ϣ���£�");
		System.out.println("ѧ��"+"\t"+"����"+"\t"+"ʱ��");
		while(rs.next())
		{
			System.out.println(rs.getString("num")+"\t"+rs.getString("name")+"\t"+rs.getString("time"));
		}
		st.close();
		dbConn.close();
		}catch(Exception e){
        	e.printStackTrace();
        	System.out.println("SQL Server����ʧ�ܣ�");
        }
		
	}
	public find(){
		Container con =this.getContentPane();//��ʼ��һ������
		txtMsg.setEditable(true);//����༭�ı���
		btnSend.setEnabled(true);//�������á�����ѧ��ѧ�š���ť
		btnStop.setEnabled(true);//�������á��˳���ѯ����ť
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
		setTitle("ѧ��������Ϣ��ѯ");
		setVisible(true);//��ʾ����
	}
	public void actionPerformed(ActionEvent e)//�¼���������������괥��
	{
		if(e.getSource()==btnSend ||e.getSource()==txtSendMsg){//����ѧ��ѧ��
			link(txtSendMsg.getText());//�������ݿ����Ӳ���ѯ��Ϣ
			txtMsg.append("ѧ��ѧ��Ϊ��"+txtSendMsg.getText()+"\n");
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}else if(e.getSource()==btnStop){
			dispose();//�رմ���
		}
	}
	public static void main(String args[]){
		new find();
	}
}

