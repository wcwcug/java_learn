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
 * 凯撒加密器 
 */  
public class WCWCaesar {  
	private static final String String = null;
	char ciphertext[]; // 密文  
	int key;  
	int flag=0;//标记位
	String[] Words;
	char plaintext[]; // 明文  
	StringBuffer plaintextStr = new StringBuffer("");  
	StringBuffer ciphertextStr = new StringBuffer("");  
	final int max = 500; // 最大字符  
  
    /** 
     * 设置密钥,返回偏移值 
     * @return 
     */  
	int setKey() {  
        System.out.println("***************** 欢迎使用凯撒加密器 *********************");  
        System.out.println("请输入一个Caesar数字密钥：");  
        while (true) {  
            Scanner sc = new Scanner(System.in);  
            try {  
                key = sc.nextInt() % 26; // %26的意义是获取密钥的偏移值  
                return key;  
           } catch (Exception e) {  
                System.out.println("Error!!!请重新输入整数密钥...");  
           }  
        }  
   }  
   /** 
     * 获得明文 
     */  
    void getPlaintext() {  
        plaintext = new char[max];  
        for (int j = 0; j < max; j++) {  
            plaintext[j] = '★'; // 设置临时变量将数组填充，因明文中可存在' '空，所以需要填充判断  
        }  
       int i = 0;  
       char ch = ' ';  
       BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));  
        System.out.println("请输入明文");  
        try {  
            ch=(char) bf.read();//获得字符  
            while(ch!='\r'&&ch!='\n'){  
                if(ch>='a'&&ch<='z'||ch>='A'&&ch<='Z'||ch==' '||ch==','||ch=='.'||ch=='!'){  
                    plaintext[i]=ch;  
                   i++;  
               }  
               else{  
                   System.out.println("输入字符不支持！！");  
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
     * 加密 
    */  
    void encryption() {  
        ciphertext = new char[max];  
        for (int j = 0; j < max; j++) {  
            ciphertext[j] = '★'; // 设置临时变量将数组填充，因明文中可存在' '空，所以需要填充判断  
        }  
        int temp = 0;  
       for (int i = 0; i < plaintext.length; i++) {  
           if (plaintext[i] != '★') {  
               temp = plaintext[i] + key;  
                if (plaintext[i] == ' ' || plaintext[i] == ',' || plaintext[i] == '.' || plaintext[i] == '!') {  
                   ciphertext[i] = (char) (plaintext[i]);  
                }  
                // ASCII码 A=65,Z=90; a=97 z=122;  
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
    * 解密 
     */  
    void deciphering() {  
       char c = ' ';  
       int temp = 0;  
       for (int i = 0; i < ciphertext.length; i++) {  
           temp = ciphertext[i] - key;  
            if (ciphertext[i] != '★') {  
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
     * 显示对比结果 
     */  
    void display() {  
        System.out.println("密文明文对比");  
        System.out.println("密文：" + ciphertextStr);  
        System.out.println("明文：" + plaintextStr); 
        String words = new String(plaintextStr);//String和StringBuffer的转换：
        Words = words.split(" ");//切分
        for (int i = 0; i < Words.length; i++) 
        {
        	flag+=compare(Words[i]);
        }
        if(flag==0)
        	System.out.println("输出皆为单词！");
        else
        	System.out.println("输出存在非单词！");
        	
    } 
    /**判断是否为单词*/
    int compare(String word)
    {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
		try {
			String str = "";
			fis = new FileInputStream("E:\\物联网安全技术\\EnglishWords.txt");// FileInputStream
			// 从文件系统中的某个文件中获取字节
			isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
			while ((str = br.readLine()) != null) {				
				if(str.equals(word))
				{
					//System.out.println("单词正确");
					return 0;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取文件失败");
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
				// 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
    }
    void To2Bin()//字节转换为二进制流
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
