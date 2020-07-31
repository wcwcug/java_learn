package my.tree;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LowTree {
  
    /*�Ƚ�����rectangle*/
    public static  boolean compare(Rectangle rectangle1,Rectangle rectangle2)
    {
    	float lowa,lowb,higha,highb;
    	/*�������½�*/
    	if(rectangle2.getLow().data[0]<rectangle2.getHigh().data[0])
    		lowa=rectangle2.getLow().data[0];
    	else
    		lowa=rectangle2.getHigh().data[0];
    	if(rectangle2.getLow().data[1]<rectangle2.getHigh().data[1])
    		lowb=rectangle2.getLow().data[1];
    	else
    		lowb=rectangle2.getHigh().data[1];
    	/*�������Ͻ�*/
    	if(rectangle2.getLow().data[0]>rectangle2.getHigh().data[0])
    		higha=rectangle2.getLow().data[0];
    	else
    		higha=rectangle2.getHigh().data[0];
    	if(rectangle2.getLow().data[1]>rectangle2.getHigh().data[1])
    		highb=rectangle2.getLow().data[1];
    	else
    		highb=rectangle2.getHigh().data[1];
	    if(rectangle1.getLow().data[0]>=lowa&&rectangle1.getLow().data[1]>=lowb&&rectangle1.getHigh().data[0]<=higha&&rectangle1.getHigh().data[1]<=highb)
	   {
		   return true;
	   }
	    else
		   return false;
    }
    /*Ѱ���ƶ��������ڵ�·������������²�R��*/
    public static void find(RTree tree,Rectangle rectangles)//Ѱ���ƶ��������ڵ�·������������²�R��
    {
    	/*�����ϲ�R����·ͼ����Ҷ�ӽ�㣨��·��*/
	      List <RTNode> list=new ArrayList<RTNode>();
			list=tree.traversePostOrder(tree.root);
			for(RTNode rTNode:list){
				for(Rectangle rectangle:rTNode.datas){
					if(rectangle!=null&&compare(rectangles,rectangle)==true)//���ƶ������ڵ�ǰrectangle��ʱ�������·��Ϣ
					{
						System.out.println(rectangles+"(�ƶ�����)��ǰ���ڵ�·��ϢΪ��"+rectangle);
						if(rectangle.lowtree==null)//����²�R��Ϊ�գ������²�R��������켣��Ϣ
						{
							RTree a = new RTree(3, 0.4f, Constants.RTREE_QUADRATIC, 2);
							rectangle.lowtree=a;
							a.insert(rectangles);
							System.out.println("�ƶ�����켣�Ѳ����²�R��");
						} 
						else//����ֱ����ԭ�е��²�R���ϲ���
						{
							rectangle.lowtree.insert(rectangles);
							System.out.println("�ƶ�����켣�Ѳ����²�R��");
						}

					}

					
				}				
			}
			
    }
	public static void main(String[] args) throws Exception   
    {  
		/*�ƶ�����켣��Ϣ*/
		LowPoint A=new LowPoint(5,5,1,1,1);
		LowPoint B=new LowPoint(7,6,2,2,1);
		LowPoint C=new LowPoint(10,5,3,3,1);
		LowPoint D=new LowPoint(12,8,4,4,1);
		LowPoint E=new LowPoint(15,8,5,5,1);
		/*�����ϲ�R��*/
		RTree tree = new RTree(3, 0.4f, Constants.RTREE_QUADRATIC, 2);
	      float[] f = {5, 10, 8, 14,    //1  
	                   5, 10, 10, 5,  
	                   10, 5, 20, 3,       //3  
	                   10, 5, 15,12,
	                   8,14,15,12,           //5
	                   8,14,21,16,
	                   20,3,21,16,          //7
	                   20,3,25,15,
	                   15,10,21,16,        //9
	                   21,16,25,15,
	      };  
	      for(int i = 0; i < f.length;)  
	      {  
	          Point p1 = new Point(new float[]{f[i++],f[i++]});  
	          Point p2 = new Point(new float[]{f[i++],f[i++]});  
	          final Rectangle rectangle = new Rectangle(p1, p2);  
	          tree.insert(rectangle);  	 
	          
	          Rectangle[] rectangles = tree.root.datas;  
	          System.out.println(tree.root.level);  
	          for(int j = 0; j < rectangles.length-1; j ++)  
	              System.out.println(rectangles[j]); 
	      }  
	      System.out.println("---------------------------------");  
	      System.out.println("High Tree insert finished.");

	         /*��������Ҷ�ӽ��*/
	      List <RTNode> list=new ArrayList<RTNode>();
			list=tree.traversePostOrder(tree.root);
			for(RTNode rTNode:list){
				for(Rectangle rectangle:rTNode.datas){
					System.out.println(rectangle);
				}
			}
		  System.out.println("---------------------------------");  
		  System.out.println("Read all road.");

          Point pa = new Point(new float[]{A.a,A.b});  
          Point pb = new Point(new float[]{B.a,B.b});  
          Point pc = new Point(new float[]{C.a,C.b}); 
          Point pd = new Point(new float[]{D.a,D.b});
          Point pe = new Point(new float[]{E.a,E.b});
          final Rectangle rectangle1 = new Rectangle(pa, pb);
          final Rectangle rectangle2 = new Rectangle(pb, pc);
          final Rectangle rectangle3 = new Rectangle(pc, pd);
          final Rectangle rectangle4 = new Rectangle(pd, pe);
          find(tree,rectangle1);
          find(tree,rectangle2);
          find(tree,rectangle3);
          find(tree,rectangle4);		  
		
    }
}
