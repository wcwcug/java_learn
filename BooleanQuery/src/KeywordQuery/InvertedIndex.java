package KeywordQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class InvertedIndex {//��������
	public final static int FileNumber=20;//�����ĵ�����
	public final static int Hop=4;//��������
	TreeMap<String,ArrayList>map=new TreeMap<String,ArrayList>();
	public TreeMap<String,ArrayList> Create()//��������������¼�����ؼ��֣����ؼ��ֵ��ĵ�id��
	{
		ArrayList al;//��������ڱ��溬�ؼ��ֵ��ĵ����
		try{
			for(int id=1;id<=FileNumber;id++)//����Ӣ���ĵ�
			{
				String line="";
				BufferedReader br=new BufferedReader(new FileReader("E:\\����洢����\\Ӣ���ĵ�\\"+id+".txt"));
				line=br.readLine();//��ȡһ��
				while(line!=null){
					String[] text=line.split("[^A-Za-z]+");//�Է���ĸ������Ϊ����ƾ��
					for(int i=0;i<text.length;i++)
					{
						text[i]=text[i].toLowerCase();//ת��ΪСд
						if(!map.containsKey(text[i])){//���map�в����ڸùؼ��֣�����ؼ��ֺ��ĵ�id
							al=new ArrayList();
							al.add(id);
							map.put(text[i], al);
						}
				        else{//���map���Ѿ������˸ùؼ��֣��ж��Ƿ��Ѵ����ĵ�id����û�������
				        	int count=0;
				        	ArrayList mylist=map.get(text[i]);
							for(int j=0;j<mylist.size();j++){
								int k=(int)mylist.get(j);
								if((int)mylist.get(j)==id){
									count++;
								}
							}
							if(count==0)
							{
								mylist.add(id);
								map.put(text[i], mylist);
							}
				        }				         
					}
					line=br.readLine();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;//����map
	}
	public String[] Split(String words)//�����з����
	{
		String[]text=words.split("[^A-Za-z]+");//�Է���ĸ������Ϊ����ƾ��
		for(int i=0;i<text.length;i++){
			text[i]=text[i].toLowerCase();//ת��ΪСд
		}
		return text;
	}
	public int[] compare(int a[],int b[])//����ϲ��������ĵ���
	{
		/*��ȥ����ʼ����0*/
		int result[] = new int[FileNumber];
		int asize=0,bsize=0;
		for(int q=0;q<FileNumber;q++)
		{
			if(a[q]>0)
				asize++;
			if(b[q]>0)
				bsize++;
		}
//		for(int q=0;q<asize;q++)
//			System.out.print(a[q]+" ");
//		System.out.println(" ");
//		for(int qq=0;qq<bsize;qq++)
//			System.out.print(b[qq]+" ");
//		System.out.println(" ");
		int i=0,j=0,k=0;
		while(i<asize && j<bsize)//��ȡ���������ڵ�����ʱ
		{
			if(a[i]==b[j])//���λ�����
			{
				result[k]=a[i];//��ŵ�c������
				k++;//������
				i++;j++;//�±����
			}
			else if(a[i]<b[j])//���aλ��С��bλ��
			{
				if((i+Hop)<asize && a[i+Hop]<=b[j])//�������һ��aλ����ȻС��bλ��
					i=i+Hop;//a��һ��
				else
					i++;
			}
			else//���aλ�ô���bλ��
			{
				if((j+Hop)<bsize && a[i]>=b[j+Hop])//�������һ��bλ����ȻС��aλ��
					j=j+Hop;//b��һ��
				else
					j++;
			}
		}
		
		return result;
	}
	public int[] and(int A[],int B[])//������
	{
		int result[] = new int[FileNumber];
		int a=0;
		int b=0;
		int t=0;
		for(int q=0;q<FileNumber;q++)
		{
			if(A[q]>0)
				a++;
			if(B[q]>0)
				b++;
		}
		int aa=0,bb=0;
		while(aa<a&&bb<b)
		{
			if(A[aa]==B[bb])//������
			{
				result[t]=A[aa];//��ŵ�������
				t++;//������
				aa++;bb++;//�±����
			}
			else if(A[aa]<B[bb])//���aλ��С��bλ��
			{
				aa++;
			}
			else//���aλ�ô���bλ��
			{
				bb++;
			}
		}
		return result;
	}
	public int[] or(int A[],int B[])//������
	{
		int result[] = new int[FileNumber];
		int a=0;
		int b=0;
		int t=0;
		for(int q=0;q<FileNumber;q++)
		{
			if(A[q]>0)
				a++;
			if(B[q]>0)
				b++;
		}
		int aa=0,bb=0,tt=0;
		while(tt<(a+b))
		{
			if(A[aa]==B[bb])//������
			{
				result[t]=A[aa];//��ŵ�c������
				t++;//������
				tt+=2;
				if(aa<a)aa++;
				if(bb<b)bb++;//�±����
			}
			else if(A[aa]<B[bb])//���aλ��С��bλ��
			{
				if(aa<a)
				{
					result[t]=A[aa];//��ŵ�c������
					aa++;
				}
				else
				{
					result[t]=B[bb];//��ŵ�c������
					bb++;
				}
				t++;
				tt++;
			}
			else
			{
				if(bb<b)
				{
					result[t]=B[bb];//��ŵ�c������
					bb++;
				}
				else
				{
					result[t]=A[aa];
					aa++;
				}
				t++;
				tt++;
				
			}
		}
		return result;
	}
	public int[] not(int A[],int B[])//������
	{
		int result[] = new int[FileNumber];
		int a=0;
		int b=0;
		for(int q=0;q<FileNumber;q++)
		{
			if(A[q]>0)
				a++;
			if(B[q]>0)
				b++;
		}
		for(int i=0;i<b;i++)
		{
			for(int j=0;j<a;j++)
			{
				if(A[j]==B[i]&&j!=a)
				{
					for(int k=j;k<a-1;k++)
					{
						A[k]=A[k+1];
					}
					a--;
				}
				if(A[a-1]==B[i])
				{
					A[a-1]=0;
				}
			}
		}
		result=A;
		return result;
	}
 	public String JumpTableMethod1(TreeMap<String,ArrayList>map,String words)//һ���ؼ��ֵĵ��ż�¼���㷨
	{
		int flag=0;
		String result =new String();
		String text=words.toLowerCase();//ת��ΪСд
	    Set<Map.Entry<String, ArrayList>> set=map.entrySet();//����map�����Ŀ����
	     for(Map.Entry<String, ArrayList> e:set){
	    	//System.out.print(text+"\t");
	    	 if(e.getKey().equals(text))
	    	 {
	    		 System.out.println("���йؼ���{"+words+"}���ĵ�������");
	    		 ArrayList mylist=e.getValue();
		         for(int i=0;i<mylist.size();i++) {
		               int alEach=(int)mylist.get(i);
		                System.out.print(alEach+".txt\t");
		                result+=alEach;
		                result+=".txt ";
			         }
		         System.out.println("");
		         flag++;
	    	 }	     
	     }
	     if(flag==0)
	     {
	    	 System.out.println("���������ĵ�������ѯ�Ĺؼ����в��ֵ���δ���ĵ��г���!!");
	    	 result="���������ĵ�������ѯ�Ĺؼ����в��ֵ���δ���ĵ��г��֣�";
	     }
	    		
	    return result;	     
	}
	public String JumpTableMethod2(TreeMap<String,ArrayList>map,String words)//��������ĵ��ż�¼���㷨(��ؼ������룩
	{
		String hello=new String();
		try{	
			String[] text=Split(words);//�зֹؼ���
			int flag=text.length;//�ؼ��ָ���
//			System.out.println("���йؼ���{"+flag+"}��");
//			for(int i=0;i<flag;i++)
//				System.out.println("���йؼ���{"+text[i]+"}");
			int ID[][]=new int[flag][FileNumber];//��ʱ����ؼ��ֶ�Ӧ���ĵ�id
			int count=0;//����
			int result[];//�����
			for(int aa=0;aa<flag;aa++)
			{
				Set<Map.Entry<String, ArrayList>> set=map.entrySet();//����map�����Ŀ����
				for(Map.Entry<String, ArrayList> e:set){
					if(count==flag)
						break;
					if(e.getKey().equals(text[count]))//�ҵ��ؼ��ֶ�Ӧ�ĵ�
					{
						System.out.print(e.getKey()+" ");
						ArrayList mylist=e.getValue();
						for(int i=0;i<mylist.size();i++) {
							ID[count][i]=(int)mylist.get(i);
							System.out.print(ID[count][i]+".txt ");
						}
						System.out.println(" ");
						count++;
					}
				}
			}
			if(count<flag){
				System.out.println("���������ĵ�������ѯ�Ĺؼ����в��ֵ���δ���ĵ��г��֣�");
				hello="���������ĵ�������ѯ�Ĺؼ����в��ֵ���δ���ĵ��г��֣�";
				return hello;
			}
			result=compare(ID[0],ID[1]);
//			for(int ss=0;ss<result.length;ss++)
//			System.out.print(result[ss]+" ");
			for(int j=2;j<flag;j++)
			{
				result=compare(result,ID[j]);
			}
			System.out.println("���йؼ���{"+words+"}���ĵ�������");
			for(int k=0;k<result.length;k++) 
			{
				if(result[k]>0)
				{
					System.out.print(result[k]+".txt\t");
					hello+=result[k];
					hello+=".txt ";
				}			
				else
					break;
			}
			System.out.println("");
				
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return hello;
	}
	public String JumpTableMethod3(TreeMap<String,ArrayList>map,String words)//��������ĵ��ż�¼���㷨(��ؼ��֣�
	{
		String result=new String();
		String[]txt=words.split(" ");//�Կո���Ϊ����ƾ��
		int flag=(txt.length)/2+1;//�ؼ��ָ���
		String[]txt2 =new String[flag];//����ؼ���		
		for(int i=0;i<flag;i++)
		{
			txt2[i]=txt[2*i];
		}
		String []splitword=new String[(txt.length)/2];//���������
		for(int i=0;i<flag-1;i++)
		{
			splitword[i]=txt[2*i+1];
		}
		int ID[][]=new int[flag][FileNumber];//��ʱ����ؼ��ֶ�Ӧ���ĵ�id
		int count=0,count2=0;//����
		int res[]=new int[FileNumber];//�����
		for(int i=0;i<flag;i++)
		{
			Set<Map.Entry<String, ArrayList>> set=map.entrySet();//����map�����Ŀ����
			for(Map.Entry<String, ArrayList> e:set){
				if(count==flag)
					break;
				if(e.getKey().equals(txt2[count]))//�ҵ��ؼ��ֶ�Ӧ�ĵ�
				{
					System.out.print(e.getKey()+" ");
					ArrayList mylist=e.getValue();
					for(int ii=0;ii<mylist.size();ii++) {
						ID[count][ii]=(int)mylist.get(ii);
						System.out.print(ID[count][ii]+".txt ");
					}
					System.out.println(" ");
					count++;
				}
			}
		}
		if(count<flag){
			System.out.println("���������ĵ�������ѯ�Ĺؼ����в��ֵ���δ���ĵ��г��֣�");
			result="���������ĵ�������ѯ�Ĺؼ����в��ֵ���δ���ĵ��г��֣�";
			return result;
		}
		int t=0;
		res=ID[0];
		for(int k=0;k<flag-1;k++)//����
		{
			if(splitword[k].equals("and")||splitword[k].equals("AND"))//������
			{
				res=and(res,ID[k+1]);
			}
			else if(splitword[k].equals("or")||splitword[k].equals("OR"))//������
			{
				res=or(res,ID[k+1]);	
			}
			else if(splitword[k].equals("not")||splitword[k].equals("NOT"))//������
			{
				res=not(res,ID[k+1]);	
			}
			else
				System.out.println("�������������д����������룡");
		}
		System.out.println("���йؼ���{"+words+"}���ĵ�������");
		for(int k=0;k<res.length;k++) 
		{
			if(res[k]>0)
			{
				System.out.print(res[k]+".txt\t");
				result+=res[k];
				result+=".txt ";
			}			
			else
				break;
		}
		System.out.println("");
		return result;
		
	}
	public static void main(String[] args){
		TreeMap<String,ArrayList> map = new TreeMap<String, ArrayList>();
		InvertedIndex a=new InvertedIndex();
		map=a.Create();
		String words="a not about not to";
		a.JumpTableMethod3(map,words);
	}
}
