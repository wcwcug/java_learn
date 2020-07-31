package Caesar;
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.Scanner;  
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/** 
 * ���������� 
 */  
public class WCWCaesar {  
	private static final String String = null;
	char ciphertext[]; // ����  
	int key;  
	int flag=0;//���λ
	String[] Words;
	char plaintext[]; // ����  
	StringBuffer plaintextStr = new StringBuffer("");  
	StringBuffer ciphertextStr = new StringBuffer("");  
	final int max = 500; // ����ַ�  
  
    /** 
     * ������Կ,����ƫ��ֵ 
     * @return 
     */  
	int setKey() {  
        System.out.println("***************** ��ӭʹ�ÿ��������� *********************");  
        System.out.println("������һ��Caesar������Կ��");  
        while (true) {  
            Scanner sc = new Scanner(System.in);  
            try {  
                key = sc.nextInt() % 26; // %26�������ǻ�ȡ��Կ��ƫ��ֵ  
                return key;  
           } catch (Exception e) {  
                System.out.println("Error!!!����������������Կ...");  
           }  
        }  
   }  
   /** 
     * ������� 
     */  
    void getPlaintext() {  
        plaintext = new char[max];  
        for (int j = 0; j < max; j++) {  
            plaintext[j] = '��'; // ������ʱ������������䣬�������пɴ���' '�գ�������Ҫ����ж�  
        }  
       int i = 0;  
       char ch = ' ';  
       BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));  
        System.out.println("����������");  
        try {  
            ch=(char) bf.read();//����ַ�  
            while(ch!='\r'&&ch!='\n'){  
                if(ch>='a'&&ch<='z'||ch>='A'&&ch<='Z'||ch==' '||ch==','||ch=='.'||ch=='!'){  
                    plaintext[i]=ch;  
                   i++;  
               }  
               else{  
                   System.out.println("�����ַ���֧�֣���");  
                   break;  
                }  
                try{  
                    ch=(char) bf.read();  
               }  
                catch(Exception e1){  
                      
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
       }  
 
    }  
  
    /** 
     * ���� 
    */  
    void encryption() {  
        ciphertext = new char[max];  
        for (int j = 0; j < max; j++) {  
            ciphertext[j] = '��'; // ������ʱ������������䣬�������пɴ���' '�գ�������Ҫ����ж�  
        }  
        int temp = 0;  
       for (int i = 0; i < plaintext.length; i++) {  
           if (plaintext[i] != '��') {  
               temp = plaintext[i] + key;  
                if (plaintext[i] == ' ' || plaintext[i] == ',' || plaintext[i] == '.' || plaintext[i] == '!') {  
                   ciphertext[i] = (char) (plaintext[i]);  
                }  
                // ASCII�� A=65,Z=90; a=97 z=122;  
               if (plaintext[i] >= 'a' && plaintext[i] <= 'z') {  
                   if (temp > 122)  
                        ciphertext[i] = (char) (97 + temp - 123);  
                    else {  
                       ciphertext[i] = (char) temp;  
                    }  
                }  
                if (plaintext[i] >= 'A' && plaintext[i] <= 'Z') {  
                    if ((plaintext[i] + key) > 90)  
                       ciphertext[i] = (char) (65 + temp - 91);  
                    else {  
                        ciphertext[i] = (char) temp;  
                    }  
                }  
                ciphertextStr.append(ciphertext[i]);  
            } else {  
                break;  
            }   
        }  
    }  

    /** 
    * ���� 
     */  
    void deciphering() {  
       char c = ' ';  
       int temp = 0;  
       for (int i = 0; i < ciphertext.length; i++) {  
           temp = ciphertext[i] - key;  
            if (ciphertext[i] != '��') {  
                if (plaintext[i] == ' ' || plaintext[i] == ',' || plaintext[i] == '.' || plaintext[i] == '!') {  
                    c = (char) (ciphertext[i]);  
               }  
                if (ciphertext[i] >= 97 && ciphertext[i] <= 122) {  
                    c = (char) (temp);  
                   if (temp < 97) {  
                        c = (char) (26 + temp);  
                    }  
                }  
                if (ciphertext[i] >= 65 && ciphertext[i] <= 90) {  
                    c = (char) (temp);  
                    if (temp < 65) {  
                        c = (char) (26 + temp);  
                    }  
                }  
                plaintextStr.append(c);  
            } else {  
                break;  
            }  
       }  
 
    }  
  
    /** 
     * ��ʾ�ԱȽ�� 
     */  
    void display() {  
        System.out.println("�������ĶԱ�");  
        System.out.println("���ģ�" + ciphertextStr);  
        System.out.println("���ģ�" + plaintextStr); 
        String words = new String(plaintextStr);//String��StringBuffer��ת����
        Words = words.split(" ");//�з�
        for (int i = 0; i < Words.length; i++) 
        {
        	flag+=compare(Words[i]);
        }
        if(flag==0)
        	System.out.println("�����Ϊ���ʣ�");
        else
        	System.out.println("������ڷǵ��ʣ�");
        	
    } 
    /**�ж��Ƿ�Ϊ����*/
    int compare(String word)
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
    void To2Bin()//�ֽ�ת��Ϊ��������
    {
  	 char a ='a';
  	 char b[] = new char[8];
    	for(int i=0;i<8;i++)
    	{
    		if((a&(1<<i))==1)
    		b[i]=1;
    		else
    			b[i]=0;
    	}
   	 for(int i=0;i<8;i++)
         System.out.println(b[i]);   
    }
	public static void main(String[] args) {  
		WCWCaesar m = new WCWCaesar();  
        m.setKey();  
        m.getPlaintext();  
        m.encryption();  
        m.deciphering();  
        m.display();  
	}

      
}  
