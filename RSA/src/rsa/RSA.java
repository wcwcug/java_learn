package rsa;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

public class RSA {
	private BigInteger RSAp,RSAq,RSAk;//������p,q,k(����[n])
	private BigInteger RSAd;//������Կd
	public  BigInteger RSAn,RSAe;//������Կn,e
	private static BigInteger x,y;//�洢��ʱ��λ�ñ���x��y ����ŷ�������չ�㷨�еĵݹ飨gcd��a��b��=ax+by��
	private static int BitLength=300;//������λ��

	public  BigInteger ex_gcd(BigInteger a,BigInteger b)//ŷ�������չ�㷨
	{
		if(b.intValue()==0)
		{
			x=new BigInteger("1");
			y=new BigInteger("0");
			return a;
		}
		BigInteger ans=ex_gcd(b,a.mod(b));//�ݹ�[gcd(a,b)=gcd(b,a mod (b))��շת�������]
		BigInteger temp=x;
		x=y;
		y=temp.subtract(a.divide(b).multiply(y));//y=temp-e/k*y
		return ans;			    
	}
	public  BigInteger cal(BigInteger e,BigInteger k)//��ģ����e,k��d
	{
		BigInteger gcd=ex_gcd(e,k);//����ŷ�������չ�㷨
		if(BigInteger.ONE.mod(gcd).intValue()!=0)//BigInteger.ONEΪ����1,gcdΪe��k�����Լ����ģ1ӦΪ0�����򷵻�-1
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
	public void GetPrime()//������Կ��������ɴ�����p��q,��p��q�ĳ˻�n���������d��
	{
		SecureRandom random=new SecureRandom();
		while(!(RSAp=BigInteger.probablePrime(BitLength,random)).isProbablePrime(1)){//���ɴ�����p
            continue;
        }
		while(!(RSAq=BigInteger.probablePrime(BitLength,random)).isProbablePrime(1)&&(RSAq==RSAp)){//���ɴ�����q
            continue;
        }
		RSAn=RSAp.multiply(RSAq);//����n
		RSAk=RSAp.subtract(BigInteger.ONE).multiply(RSAq.subtract(BigInteger.ONE));//����k
		RSAe=BigInteger.probablePrime(BitLength-1, random);//����e
		RSAd=cal(RSAe,RSAk);
	}
	public  String encrypt(String source,String charset)//����
	{
		byte[] sourceByte = null;
		try {
			sourceByte = source.getBytes(charset);
		    } catch (UnsupportedEncodingException e) {
		    	e.printStackTrace();
		    }
		BigInteger temp=new BigInteger(1,sourceByte);
		BigInteger encrypted=temp.modPow(RSAe, RSAn);//����һ��BigInteger����ֵ�� (RSAe^RSAn mod temp)
		return Base64.encodeBase64String(encrypted.toByteArray());//Base64��
	}
	public String decrypt(String cryptdata,String charset) throws UnsupportedEncodingException//����
	{
		byte[] byteTmp=Base64.decodeBase64(cryptdata);
		BigInteger cryptedBig=new BigInteger(byteTmp);
		byte[] cryptedData=cryptedBig.modPow(RSAd, RSAn).toByteArray();
		/*BigInteger���з���λ�ġ�����StringʱҪ�ǵ�ȥ������λ����Ȼ���ĵ�ʱ���Ī�������ڵ�һλ����ո�*/
		String result=new String(cryptedData,charset);
		char test= result.charAt(0);//��ȡ�ַ�����λ�ַ�
		if((int)test==0)//����0��Ӧ�ַ��еĿո�
		{
			result=result.substring(1,result.length());//ȥ���ո�
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
	//����
    public static void main(String args[]) {
    	 System.out.println("***RSA�㷨���ԣ���������***");
    	//�����������
    	   Scanner sc=new Scanner(System.in);
    	//��ȡ�û�������ַ���
    	   System.out.print("��������������:");
    	   String source=sc.nextLine();
    		RSA a=new RSA();
    		a.GetPrime();//������Կ
    		System.out.print("��������p��ֵΪ:");
    		a.output(a.RSAp);
    		System.out.print("��������q��ֵΪ:");
    		a.output(a.RSAq);
    		System.out.print("��������n��ֵΪ:");
    		a.output(a.RSAn);
    		System.out.print("��������e��ֵΪ:");
    		a.output(a.RSAe);
    		System.out.print("��������d��ֵΪ:");
    		a.output(a.RSAd);
    		//String source = new String("666�����������繤��רҵ193141��ѧ����over");
    		System.out.println("����:"+source);
    		String cryptdata=a.encrypt(source, "UTF-8");
    		System.out.print("��������Ϊ:");
    		char[] p = cryptdata.toString().toCharArray();
    		for(int i=0;i<p.length;i++){
    			  if(i%16==0)
    				  System.out.println(" "); 
    			  System.out.print(p[i]+" ");
    			 } 
    		System.out.println(" ");
    		try {
    			            String result=a.decrypt(cryptdata,"UTF-8");
    			            System.out.println("���ܺ�:"+result);
    			            if(result.equals(source))
    			            System.out.println("���ܳɹ�������");
    			         } catch (UnsupportedEncodingException e) {
    			        	 e.printStackTrace();
    			         }

    }

	
}
