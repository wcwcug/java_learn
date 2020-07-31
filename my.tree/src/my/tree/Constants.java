package my.tree; 
public class Constants {//常量
	 public static final int MAX_NUMBER_OF_ENTRIES_IN_NODE = 20;//结点中的最大条目数  
	    public static final int MIN_NUMBER_OF_ENTRIES_IN_NODE = 8;//结点中的最小条目数  
	      
	    public static final int RTDataNode_Dimension = 2;  //维数
	      
	    /** Available RTree variants. */  
	    public static final int RTREE_LINEAR = 0;  //线性
	    public static final int RTREE_QUADRATIC = 1;  //二次
	    public static final int RTREE_EXPONENTIAL = 2;  //指数
	    public static final int RSTAR = 3;  //中继
	      
	    public static final int NIL = -1;  
	    public static final RTNode NULL = null; 
}
