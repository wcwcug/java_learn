package my.tree;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileReader;  
import java.util.ArrayList;  
import java.util.List;  

public class RTree {
	/** 
     * 根节点 
     */  
	RTNode root;  
      
    /** 
     * 树类型 
     */  
    private int tree_type;  
      
    /** 
     * 结点容量 
     */  
    private int nodeCapacity = -1;  
      
    /** 
     * 结点填充因子 
     */  
    private float fillFactor = -1;  
      
    private int dimension ;  //维数
      
    public RTree(int capacity, float fillFactor, int type, int dimension)  //构造函数
    {  
        this.fillFactor = fillFactor;  
        tree_type = type;  
        nodeCapacity = capacity;  
        this.dimension = dimension;  
        root = new RTDataNode(this,Constants.NULL);  
    }  
      
    /** 
     * @return RTree的维度 
     */  
    public int getDimension()  
    {  
        return dimension;  
    }  
      
    public void setRoot(RTNode root)  
    {  
        this.root = root;  
    }  
      
    public float getFillFactor()  
    {  
        return fillFactor;  
    }  
      
    /** 
     * @return 返回结点容量 
     */  
    public int getNodeCapacity()   
    {  
        return nodeCapacity;  
    }  
      
    /** 
     * @return 返回树的类型 
     */  
    public int getTreeType()  
    {  
        return tree_type;  
    }  
      
    /** 
     * 向Rtree中插入Rectangle<p> 
     * 1、先找到合适的叶节点 <br> 
     * 2、再向此叶节点中插入<br> 
     * @param rectangle 
     */  
    public boolean insert(Rectangle rectangle)  
    {  
        if(rectangle == null)  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
          
        if(rectangle.getHigh().getDimension() != getDimension())  
        {  
            throw new IllegalArgumentException("Rectangle dimension different than RTree dimension.");  
        }  
          
        RTDataNode leaf = root.chooseLeaf(rectangle);  
          
        return leaf.insert(rectangle);  
    }  
      
    /** 
     * 从R树中删除Rectangle <p> 
     * 1、寻找包含记录的结点--调用算法findLeaf()来定位包含此记录的叶子结点L，如果没有找到则算法终止。<br> 
     * 2、删除记录--将找到的叶子结点L中的此记录删除<br> 
     * 3、调用算法condenseTree<br> 
     * @param rectangle 
     * @return 
     */  
    public int delete(Rectangle rectangle)  
    {  
        if(rectangle == null)  
        {  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
        }  
          
        if(rectangle.getHigh().getDimension() != getDimension())  
        {  
            throw new IllegalArgumentException("Rectangle dimension different than RTree dimension.");  
        }  
          
        RTDataNode leaf = root.findLeaf(rectangle);  
          
        if(leaf != null)  
        {  
            return leaf.delete(rectangle);  
        }  
          
        return -1;  
    }  
      
    /** 
     * 从给定的结点root开始遍历所有的叶子结点 
     * @param node 
     * @return 所有遍历的结点集合 
     */  
    public List<RTNode> traversePostOrder(RTNode root)   
    {  
        if(root == null)  
            throw new IllegalArgumentException("Node cannot be null.");  
          
        List<RTNode> list = new ArrayList<RTNode>();  
        

        if(! root.isLeaf())  
        {  
            for(int i = 0; i < root.usedSpace; i ++)  
            {  
                List<RTNode> a = traversePostOrder(((RTDirNode)root).getChild(i));  
                for(int j = 0; j < a.size(); j ++)  
                {  
                    list.add(a.get(j));  
                }  
            }  
        }
        else{
        	list.add(root);  
        }
          
        return list;  
    }  
      
    public static void main(String[] args) throws Exception   
    {  
     RTree tree = new RTree(5, 0.4f, Constants.RTREE_QUADRATIC, 2);  
        
      float[] f = {10, 20, 40, 70,    //1  
              30, 10, 70, 15,  
              100, 70, 110, 80,       //3  
               0, 50, 30, 55,  
               13, 21, 54, 78,        //5  
               3, 8, 23, 34,  
              200, 29, 202, 50,  
               34, 1, 35, 1,          //8  
               201, 200, 234, 203,  
               56, 69, 58, 70,        //10  
              2, 67, 270, 102,  
               1, 10, 310, 20,        //12  
              23, 12, 345, 120,  
               5, 34, 100, 340,  
               19,100,450,560,    //15  
               12,340,560,450,  
               34,45,190,590,  
               24,47,770,450, //18                  
              91,99,390,980,  
               89,10,99,100,  //20  
               10,29,400,990,  
               110,220,220,330,  
               123,24,234,999 //23  
      };  
        
      //插入结点  
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
      System.out.println("Insert finished.");
         /*遍历所有叶子结点*/
      List <RTNode> list=new ArrayList<RTNode>();
		list=tree.traversePostOrder(tree.root);
		for(RTNode rTNode:list){
			for(Rectangle rectangle:rTNode.datas){
				System.out.println(rectangle);
			}
		}
          


  
//          
//      //删除结点  
//      System.out.println("---------------------------------");  
//      System.out.println("Begin delete.");  
//        
//      for(int i = 0; i < f.length;)  
//      {  
//         Point p1 = new Point(new float[]{f[i++],f[i++]});  
//          Point p2 = new Point(new float[]{f[i++],f[i++]});  
//          final Rectangle rectangle = new Rectangle(p1, p2);  
//          tree.delete(rectangle);  
//            
//          Rectangle[] rectangles = tree.root.datas;  
//          System.out.println(tree.root.level);  
//         for(int j = 0; j < rectangles.length; j ++)  
//             System.out.println(rectangles[j]);  
//      }  
//       
//     System.out.println("---------------------------------");  
//     System.out.println("Delete finished.");  
//          
//          
//      Rectangle[] rectangles = tree.root.datas;  
//      for(int i = 0; i < rectangles.length; i ++)  
//          System.out.println(rectangles[i]);  
          
          
    } 
}
