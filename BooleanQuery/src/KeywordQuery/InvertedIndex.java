package KeywordQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class InvertedIndex {//倒排索引
	public final static int FileNumber=20;//定义文档个数
	public final static int Hop=4;//定义跳数
	TreeMap<String,ArrayList>map=new TreeMap<String,ArrayList>();
	public TreeMap<String,ArrayList> Create()//创建倒排索引记录表，（关键字，含关键字的文档id）
	{
		ArrayList al;//数组表，用于保存含关键字的文档标号
		try{
			for(int id=1;id<=FileNumber;id++)//遍历英文文档
			{
				String line="";
				BufferedReader br=new BufferedReader(new FileReader("E:\\网络存储技术\\英文文档\\"+id+".txt"));
				line=br.readLine();//读取一行
				while(line!=null){
					String[] text=line.split("[^A-Za-z]+");//以非字母符号作为划分凭据
					for(int i=0;i<text.length;i++)
					{
						text[i]=text[i].toLowerCase();//转换为小写
						if(!map.containsKey(text[i])){//如果map中不存在该关键字，加入关键字和文档id
							al=new ArrayList();
							al.add(id);
							map.put(text[i], al);
						}
				        else{//如果map中已经保存了该关键字，判断是否已存入文档id，若没有则加入
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
		return map;//返回map
	}
	public String[] Split(String words)//按行切分语句
	{
		String[]text=words.split("[^A-Za-z]+");//以非字母符号作为划分凭据
		for(int i=0;i<text.length;i++){
			text[i]=text[i].toLowerCase();//转换为小写
		}
		return text;
	}
	public int[] compare(int a[],int b[])//跳表合并（两个文档）
	{
		/*先去除初始化的0*/
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
		while(i<asize && j<bsize)//当取两个数组内的数字时
		{
			if(a[i]==b[j])//如果位置相等
			{
				result[k]=a[i];//存放到c数组中
				k++;//计数器
				i++;j++;//下标后移
			}
			else if(a[i]<b[j])//如果a位置小于b位置
			{
				if((i+Hop)<asize && a[i+Hop]<=b[j])//如果跳了一次a位置仍然小于b位置
					i=i+Hop;//a跳一次
				else
					i++;
			}
			else//如果a位置大于b位置
			{
				if((j+Hop)<bsize && a[i]>=b[j+Hop])//如果跳了一次b位置仍然小于a位置
					j=j+Hop;//b跳一次
				else
					j++;
			}
		}
		
		return result;
	}
	public int[] and(int A[],int B[])//与运算
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
			if(A[aa]==B[bb])//如果相等
			{
				result[t]=A[aa];//存放到数组中
				t++;//计数器
				aa++;bb++;//下标后移
			}
			else if(A[aa]<B[bb])//如果a位置小于b位置
			{
				aa++;
			}
			else//如果a位置大于b位置
			{
				bb++;
			}
		}
		return result;
	}
	public int[] or(int A[],int B[])//或运算
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
			if(A[aa]==B[bb])//如果相等
			{
				result[t]=A[aa];//存放到c数组中
				t++;//计数器
				tt+=2;
				if(aa<a)aa++;
				if(bb<b)bb++;//下标后移
			}
			else if(A[aa]<B[bb])//如果a位置小于b位置
			{
				if(aa<a)
				{
					result[t]=A[aa];//存放到c数组中
					aa++;
				}
				else
				{
					result[t]=B[bb];//存放到c数组中
					bb++;
				}
				t++;
				tt++;
			}
			else
			{
				if(bb<b)
				{
					result[t]=B[bb];//存放到c数组中
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
	public int[] not(int A[],int B[])//非运算
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
 	public String JumpTableMethod1(TreeMap<String,ArrayList>map,String words)//一个关键字的倒排记录表算法
	{
		int flag=0;
		String result =new String();
		String text=words.toLowerCase();//转换为小写
	    Set<Map.Entry<String, ArrayList>> set=map.entrySet();//返回map里的条目规则集
	     for(Map.Entry<String, ArrayList> e:set){
	    	//System.out.print(text+"\t");
	    	 if(e.getKey().equals(text))
	    	 {
	    		 System.out.println("含有关键字{"+words+"}的文档包括：");
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
	    	 System.out.println("遍历所有文档，您查询的关键字中部分单词未在文档中出现!!");
	    	 result="遍历所有文档，您查询的关键字中部分单词未在文档中出现！";
	     }
	    		
	    return result;	     
	}
	public String JumpTableMethod2(TreeMap<String,ArrayList>map,String words)//基于跳表的倒排记录表算法(多关键字相与）
	{
		String hello=new String();
		try{	
			String[] text=Split(words);//切分关键字
			int flag=text.length;//关键字个数
//			System.out.println("含有关键字{"+flag+"}个");
//			for(int i=0;i<flag;i++)
//				System.out.println("含有关键字{"+text[i]+"}");
			int ID[][]=new int[flag][FileNumber];//暂时保存关键字对应的文档id
			int count=0;//计数
			int result[];//结果表
			for(int aa=0;aa<flag;aa++)
			{
				Set<Map.Entry<String, ArrayList>> set=map.entrySet();//返回map里的条目规则集
				for(Map.Entry<String, ArrayList> e:set){
					if(count==flag)
						break;
					if(e.getKey().equals(text[count]))//找到关键字对应文档
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
				System.out.println("遍历所有文档，您查询的关键字中部分单词未在文档中出现！");
				hello="遍历所有文档，您查询的关键字中部分单词未在文档中出现！";
				return hello;
			}
			result=compare(ID[0],ID[1]);
//			for(int ss=0;ss<result.length;ss++)
//			System.out.print(result[ss]+" ");
			for(int j=2;j<flag;j++)
			{
				result=compare(result,ID[j]);
			}
			System.out.println("含有关键字{"+words+"}的文档包括：");
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
	public String JumpTableMethod3(TreeMap<String,ArrayList>map,String words)//不含跳表的倒排记录表算法(多关键字）
	{
		String result=new String();
		String[]txt=words.split(" ");//以空格作为划分凭据
		int flag=(txt.length)/2+1;//关键字个数
		String[]txt2 =new String[flag];//保存关键字		
		for(int i=0;i<flag;i++)
		{
			txt2[i]=txt[2*i];
		}
		String []splitword=new String[(txt.length)/2];//保存运算符
		for(int i=0;i<flag-1;i++)
		{
			splitword[i]=txt[2*i+1];
		}
		int ID[][]=new int[flag][FileNumber];//暂时保存关键字对应的文档id
		int count=0,count2=0;//计数
		int res[]=new int[FileNumber];//结果表
		for(int i=0;i<flag;i++)
		{
			Set<Map.Entry<String, ArrayList>> set=map.entrySet();//返回map里的条目规则集
			for(Map.Entry<String, ArrayList> e:set){
				if(count==flag)
					break;
				if(e.getKey().equals(txt2[count]))//找到关键字对应文档
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
			System.out.println("遍历所有文档，您查询的关键字中部分单词未在文档中出现！");
			result="遍历所有文档，您查询的关键字中部分单词未在文档中出现！";
			return result;
		}
		int t=0;
		res=ID[0];
		for(int k=0;k<flag-1;k++)//运算
		{
			if(splitword[k].equals("and")||splitword[k].equals("AND"))//与运算
			{
				res=and(res,ID[k+1]);
			}
			else if(splitword[k].equals("or")||splitword[k].equals("OR"))//或运算
			{
				res=or(res,ID[k+1]);	
			}
			else if(splitword[k].equals("not")||splitword[k].equals("NOT"))//或运算
			{
				res=not(res,ID[k+1]);	
			}
			else
				System.out.println("您输入的运算符有错，请重新输入！");
		}
		System.out.println("含有关键字{"+words+"}的文档包括：");
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
