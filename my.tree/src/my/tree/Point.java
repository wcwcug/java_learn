package my.tree;

public class Point {
	  float[] data;  
     
	    public Point(float[] data)  
	    {  
	        if(data == null)  
	        {  
	            throw new IllegalArgumentException("Coordinates(坐标) cannot be null.");  
	        }  
	        if(data.length < 2)  
	        {  
	            throw new IllegalArgumentException("Point dimension should be greater than 1.");  
	        }  
	          
	        this.data = new float[data.length];  
	        System.arraycopy(data, 0, this.data, 0, data.length);  //复制data
	    }  
	      
//	    public Point(int[] data)  
//	    {  
//	        if(data == null)  
//	        {  
//	            throw new IllegalArgumentException("Coordinates cannot be null.");  
//	        }  
//	        if(data.length < 2)  
//	        {  
//	            throw new IllegalArgumentException("Point dimension should be greater than 1.");  
//	        }  
//	          
//	        this.data = new float[data.length];  
//	        for(int i = 0 ; i < data.length ; i ++)  
//	        {  
//	            this.data[i] = data[i];  
//	        }  
//	    }  
	      
	    @Override  
	    protected Object clone()  
	    {  
	        float[] copy = new float[data.length];  
	        System.arraycopy(data, 0, copy, 0, data.length);  
	        return new Point(copy);  
	    }  
	      
	    @Override  
	    public String toString()   //生成坐标点
	    {  
	        StringBuffer sBuffer = new StringBuffer("(");  //字符串
	          
	        for(int i = 0 ; i < data.length - 1 ; i ++)  
	        {  
	            sBuffer.append(data[i]).append(",");  
	        }  
	          
	        sBuffer.append(data[data.length - 1]).append(")");  
	          
	        return sBuffer.toString();  
	    }  
	      
//	    public static void main(String[] args)   
//	    {  
//	        float[] test = {1.2f,2f,34f};  
//	        Point point1 = new Point(test);  
//	        System.out.println(point1);  
//
//	        float[] test3 = {1,2,34};  
//	        Point point3 = new Point(test3);  
//	        System.out.println(point3); 
//	    }  
	  
	    /** 
	     * @return 返回Point的维度 
	     */  
	    public int getDimension()   //返回Point的维度 
	    {  
	        return data.length;  
	    }  
	  
	    /** 
	     * @param index 
	     * @return 返回Point坐标第i位的float值 
	     */  
	    public float getFloatCoordinate(int index)   //返回Point坐标第i位的float值 
	    {  
	        return data[index];  
	    }  
//	      
//	    /** 
//	     * @param index 
//	     * @return 返回Point坐标第i位的int值 
//	     */  
//	    public int getIntCoordinate(int index)  
//	    {  
//	        return (int) data[index];  
//	    }  
	      
//	    @Override  
//	    public boolean equals(Object obj)   
//	    {  
//	        if(obj instanceof Point)  //判断其左边对象是否为其右边类的实例
//	        {  
//	            Point point = (Point) obj;  
//	              
//	            if(point.getDimension() != getDimension())  
//	                throw new IllegalArgumentException("Points must be of equal dimensions to be compared.");  
//	              
//	            for(int i = 0; i < getDimension(); i ++)  
//	            {  
//	                if(getFloatCoordinate(i) != point.getFloatCoordinate(i))  
//	                    return false;  
//	            }  
//	        }  
//	          
//	        if(! (obj instanceof Point))  
//	            return false;  
//	          
//	        return true;  
//	    }  
}
