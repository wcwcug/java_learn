package rsa;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

public class RSA {
	private BigInteger RSAp,RSAq,RSAk;//大素数p,q,k(即Ψ[n])
	private BigInteger RSAd;//解密密钥d
	public  BigInteger RSAn,RSAe;//加密密钥n,e
	private static BigInteger x,y;//存储临时的位置变量x，y 用于欧几里得扩展算法中的递归（gcd（a，b）=ax+by）
	private static int BitLength=300;//大素数位数

	public  BigInteger ex_gcd(BigInteger a,BigInteger b)//欧几里得扩展算法
	{
		if(b.intValue()==0)
		{
			x=new BigInteger("1");
			y=new BigInteger("0");
			return a;
		}
		BigInteger ans=ex_gcd(b,a.mod(b));//递归[gcd(a,b)=gcd(b,a mod (b))（辗转相除法）]
		BigInteger temp=x;
		x=y;
		y=temp.subtract(a.divide(b).multiply(y));//y=temp-e/k*y
		return ans;			    
	}
	public  BigInteger cal(BigInteger e,BigInteger k)//求反模，由e,k求d
	{
		BigInteger gcd=ex_gcd(e,k);//调用欧几里得扩展算法
		if(BigInteger.ONE.mod(gcd).intValue()!=0)//BigInteger.ONE为常量1,gcd为e和k的最大公约数，模1应为0，否则返回-1
		{
			return new BigInteger("-1");
		}
		x=x.multiply(BigInteger.ONE.divide(gcd));
		k=k.abs();
		BigInteger ans=x.mod(k);
		if(ans.compareTo(BigInteger.ZERO)<0) 
			ans=ans.add(k);
		return ans;
	}
	public void GetPrime()//生成密钥（随机生成大素数p和q,及p和q的乘积n；并计算出d）
	{
		SecureRandom random=new SecureRandom();
		while(!(RSAp=BigInteger.probablePrime(BitLength,random)).isProbablePrime(1)){//生成大素数p
            continue;
        }
		while(!(RSAq=BigInteger.probablePrime(BitLength,random)).isProbablePrime(1)&&(RSAq==RSAp)){//生成大素数q
            continue;
        }
		RSAn=RSAp.multiply(RSAq);//生成n
		RSAk=RSAp.subtract(BigInteger.ONE).multiply(RSAq.subtract(BigInteger.ONE));//生成k
		RSAe=BigInteger.probablePrime(BitLength-1, random);//生成e
		RSAd=cal(RSAe,RSAk);
	}
	public  String encrypt(String source,String charset)//加密
	{
		byte[] sourceByte = null;
		try {
			sourceByte = source.getBytes(charset);
		    } catch (UnsupportedEncodingException e) {
		    	e.printStackTrace();
		    }
		BigInteger temp=new BigInteger(1,sourceByte);
		BigInteger encrypted=temp.modPow(RSAe, RSAn);//返回一个BigInteger，其值是 (RSAe^RSAn mod temp)
		return Base64.encodeBase64String(encrypted.toByteArray());//Base64码
	}
	public String decrypt(String cryptdata,String charset) throws UnsupportedEncodingException//解密
	{
		byte[] byteTmp=Base64.decodeBase64(cryptdata);
		BigInteger cryptedBig=new BigInteger(byteTmp);
		byte[] cryptedData=cryptedBig.modPow(RSAd, RSAn).toByteArray();
		/*BigInteger是有符号位的。重组String时要记得去除符号位，不然中文的时候会莫名其妙在第一位多出空格。*/
		String result=new String(cryptedData,charset);
		char test= result.charAt(0);//读取字符串首位字符
		if((int)test==0)//数字0对应字符中的空格
		{
			result=result.substring(1,result.length());//去除空格
		}	
		return result;
	}
	public void output(BigInteger a)
	{
		char[] p = a.toString().toCharArray();
		for(int i=0;i<p.length;i++){
			  if(i%16==0)
				  System.out.println(" "); 
			  System.out.print(p[i]+" ");
			 } 
		System.out.println(" ");
	}
	//测试
    public static void main(String args[]) {
    	 System.out.println("***RSA算法测试（王晨威）***");
    	//创建输入对象
    	   Scanner sc=new Scanner(System.in);
    	//获取用户输入的字符串
    	   System.out.print("请输入任意明文:");
    	   String source=sc.nextLine();
    		RSA a=new RSA();
    		a.GetPrime();//生成密钥
    		System.out.print("本次运算p的值为:");
    		a.output(a.RSAp);
    		System.out.print("本次运算q的值为:");
    		a.output(a.RSAq);
    		System.out.print("本次运算n的值为:");
    		a.output(a.RSAn);
    		System.out.print("本次运算e的值为:");
    		a.output(a.RSAe);
    		System.out.print("本次运算d的值为:");
    		a.output(a.RSAd);
    		//String source = new String("666王晨威是网络工程专业193141班学生！over");
    		System.out.println("明文:"+source);
    		String cryptdata=a.encrypt(source, "UTF-8");
    		System.out.print("加密密文为:");
    		char[] p = cryptdata.toString().toCharArray();
    		for(int i=0;i<p.length;i++){
    			  if(i%16==0)
    				  System.out.println(" "); 
    			  System.out.print(p[i]+" ");
    			 } 
    		System.out.println(" ");
    		try {
    			            String result=a.decrypt(cryptdata,"UTF-8");
    			            System.out.println("解密后:"+result);
    			            if(result.equals(source))
    			            System.out.println("解密成功！！！");
    			         } catch (UnsupportedEncodingException e) {
    			        	 e.printStackTrace();
    			         }

    }

	
}
