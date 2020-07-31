package chat;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
class Client extends JFrame implements ActionListener{//�û���
	Socket sock;
	JTextArea txtMsg = new JTextArea();//����һ���µ��ı���
	JTextField txtSendMsg=new JTextField(20);//������һ�����Ϊ 20 ���ַ��ĵ����ı��� 
	JButton btnSend=new JButton("����");//����һ�����Ͱ�ť
	JButton btnConnect=new JButton("����������");//���������������ҡ���ť
	JButton btnDisConnect=new JButton("�˳�������");//�������˳������ҡ���ť
	DataOutputStream out;//�������������
	DataInputStream in;//��������������
	boolean canWaiter=true;
	Waiter waiter;//����һ���߳�
	private String name1;
	public void outfile(){//��ȡ��Ϣ��¼
		try{
			String outfile1="D:\\chatData.txt";//��һ���ļ�
			BufferedReader in =new BufferedReader(new FileReader(outfile1));
			String line;
			line=in.readLine();//��ȡһ������
			while(line!=null){
    		txtMsg.append("***    "+line);
    		txtMsg.append("\n");
    		line=in.readLine();
    		}
    		in.close();
    	}catch(IOException ex){
    		
    	}
	}
	public Client(String name)
	{
		name1=name;//�û���
		//System.out.println(name1);
		Container con =this.getContentPane();//��ʼ��һ������
		txtMsg.setEditable(false);//��ֹ�༭�ı���
		btnConnect.setEnabled(true);//�������á����ӷ���������ť
		btnDisConnect.setEnabled(false);//��ֹ���á��Ͽ����ӡ���ť
		btnSend.setEnabled(false);//��ֹ���á����͡���ť
		txtSendMsg.setEditable(false);//��ֹ�༭�ı���
		/*����JPanel�����������*/
		JPanel p1=new JPanel();
		JScrollPane p2=new JScrollPane(txtMsg);//�������
		JPanel p3=new JPanel();
		p1.add(btnConnect);
		p1.add(btnDisConnect);
		p3.add(txtSendMsg);
		p3.add(btnSend);		
		con.add(p1, "North");
		con.add(p2, "Center");
		con.add(p3, "South");
		/*����*/
		txtSendMsg.addActionListener(this);
		btnSend.addActionListener(this);
		btnConnect.addActionListener(this);
		btnDisConnect.addActionListener(this);

		addWindowListener(new WindowAdapter(){//frame׷��һ��windows�¼�����
			public void windowClosing(WindowEvent e){
				try{
					disconnect();//�Ͽ�����
				}catch(Exception ee){					
				}
				dispose();//�رմ����ͷŸô����ռ�õ���Ļ��Դ
				System.exit(0);	//ֹͣ�߳�			
			}
		});
		//setTitle("����С��������");
		setSize(400,400);
		setVisible(true);//��ʾ����
		connect();//���ӷ�����
	}
	private void connect(){//���ӷ�����
		try{
			sock=new Socket("127.0.0.1",8800);//��������������ӵ��׽���
			//System.out.println("��½����");
			OutputStream os=sock.getOutputStream();//�����׽��ֻ�������
			InputStream is=sock.getInputStream();//�����׽��ֻ��������
			out =new DataOutputStream(os);//�����׽��ֽ������������
			in=new DataInputStream(is);//�����׽��ֽ�������������
//			txtMsg.append(name1+"   ��½�����ҳɹ�\n");//�ڿͻ����ı�������������
			setTitle(name1);
			txtMsg.append("*****��������Ϣ��¼*****\n");
			outfile();//����Ϣ��¼
			txtMsg.append("\n\n\n\n\n");
			out.writeUTF("��ϵͳ��Ϣ��"+name1+"����������\n");
			btnConnect.setEnabled(false);//��ֹ���á����ӷ���������ť
			btnDisConnect.setEnabled(true);//�������á��Ͽ����ӡ���ť
			btnSend.setEnabled(true);//�������á����͡���ť
			txtSendMsg.setEditable(true);//����༭�ı���
			waiter =new Waiter();//�����߳�
			waiter.start();//�����߳�
		}catch(Exception ee){
			JOptionPane.showMessageDialog(null, "���ӷ�����ʧ�ܣ�");
		}
	}
	private void sendMsg(){//������Ϣ
			try{
				SimpleDateFormat formater=new SimpleDateFormat("HH:mm:ss");//���ʵʱʱ��
				String time=formater.format(new Date());
				out.writeUTF(time+"  "+name1+"��"+txtSendMsg.getText());//�������������Ϣ
			}catch(Exception ee){
				JOptionPane.showMessageDialog(null, "������Ϣʧ�ܣ�");
			}
		}
	private void disconnect(){//�Ͽ�����
		btnConnect.setEnabled(true);//�������á����ӷ���������ť
		btnDisConnect.setEnabled(false);//��ֹ���á��Ͽ����ӡ���ť
		btnSend.setEnabled(false);//��ֹ���á����͡���ť
		txtSendMsg.setEditable(false);//��ֹ�༭�ı���
		try{
			out.writeUTF("��ϵͳ��Ϣ��"+name1+"�˳�������\n");//д�������
		}catch(Exception ex){			
		}finally{
			canWaiter=false;
			try{
				in.close();//�ر�������
				out.close();//�ر������
			}catch(Exception ex){			
			}finally{
				try{
					sock.close();//�ر��׽���
				}catch(Exception ex){			
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e)//�¼���������������괥��
	{
		if(e.getSource()==btnSend ||e.getSource()==txtSendMsg){//������Ϣ��༭�ı��򣬲������ı���
			sendMsg();
			txtSendMsg.setText("");
			txtSendMsg.requestFocus();
		}else if(e.getSource()==btnConnect){//���ӷ�����
			canWaiter=true;
			connect();
		}else if(e.getSource()==btnDisConnect){//�Ͽ�����
			disconnect();
		}
	}
	private class Waiter extends Thread{//���ڽ�����Ϣ���߳�
		public void run(){
			String msg=null;
			while(canWaiter){
				try{
					msg=in.readUTF();//�����������ϵ�����
					if(msg.equals("serverStop")){
						txtMsg.append("������ֹͣ\n");
						break;
					}
//					SimpleDateFormat formater=new SimpleDateFormat("HH:mm:ss");//���ʵʱʱ��
//					String time=formater.format(new Date());
					txtMsg.append(msg+"\n");//������û�����
					//txtMsg.append(time+"  "+name1+"��"+msg+"\n");
				}catch(IOException ex){
					break;
				}
			}
			txtMsg.append("�ͻ����� \n");
			disconnect();
		}
	}
public static void main(String args[]){
		new Client("fnw");

	}
}
