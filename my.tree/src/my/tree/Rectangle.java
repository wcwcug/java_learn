package my.tree;

public class Rectangle implements Cloneable{//�߽����
	private Point low;  
	private Point high;  
    /*Ҷ�ӽ�������²�R��*/
    RTree lowtree; 
    public Rectangle(RTree lowtree) 
    {
    	this.lowtree=lowtree;
    }
    public Rectangle(Point p1, Point p2)  
    {  
        if(p1 == null || p2 == null)  
        {  
            throw new IllegalArgumentException("Points cannot be null.");  
        }  
        if(p1.getDimension() != p2.getDimension())  
        {  
            throw new IllegalArgumentException("Points must be of same dimension.");  
        }  
        low=p1;
        high=p2;
//        low = (Point) p1.clone();  
//        high = (Point) p2.clone();    
    }  
      
    /** 
     * ����Rectangle���½ǵ�Point 
     * @return Point 
     */  
    public Point getLow()   
    {  
        return low;  
    }  
  
    /** 
     * ����Rectangle���Ͻǵ�Point 
     * @return Point 
     */  
    public Point getHigh()   
    {  
        return high;  
    }

    /** 
     * @param rectangle 
     * @return ��Χ����Rectangle����СRectangle 
     */  
    public Rectangle getUnionRectangle(Rectangle rectangle)  //��Χ����Rectangle����СRectangle 
    {  
        if(rectangle == null)  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
          
        if(rectangle.getDimension() != getDimension())  
        {  
            throw new IllegalArgumentException("Rectangle must be of same dimension.");  
        }  
          
        float[] min = new float[getDimension()];  
        float[] max = new float[getDimension()];  
          
        for(int i = 0; i < getDimension(); i ++)  
        {  
            min[i] = Math.min(low.getFloatCoordinate(i), rectangle.low.getFloatCoordinate(i));  //���½ǽ��
            max[i] = Math.max(high.getFloatCoordinate(i), rectangle.high.getFloatCoordinate(i)); //���Ͻǽ�� 
        }  
          
        return new Rectangle(new Point(min), new Point(max));  //������СRectangle 
    }  
      
    /** 
     * @return ����Rectangle����� 
     */  
    public float getArea()  
    {  
        float area = 1;  
        for(int i = 0; i < getDimension(); i ++)  
        {  
            area *= high.getFloatCoordinate(i) - low.getFloatCoordinate(i);  
        }  
          
        return area;  
    }  
      
    /** 
     * @param rectangles 
     * @return ��Χһϵ��Rectangle����СRectangle 
     */  
    public static Rectangle getUnionRectangle(Rectangle[] rectangles)  
    {  
        if(rectangles == null || rectangles.length == 0)  
            throw new IllegalArgumentException("Rectangle array is empty.");  
          
        Rectangle r0 = (Rectangle) rectangles[0].clone();  
        for(int i = 1; i < rectangles.length; i ++)  
        {  
            r0 = r0.getUnionRectangle(rectangles[i]);  
        }  
          
        return r0;  
    }  
      
    @Override  
    protected Object clone()  
    {  
        Point p1 = (Point) low.clone();  
        Point p2 = (Point) high.clone();  
        return new Rectangle(p1, p2);  
    }  
      
    @Override  
    public String toString()   
    {  
        return "Rectangle Low:" + low + " High:" + high;  
    }  
      
    public static void main(String[] args)   
    {  
        float[] f1 = {1.3f,2.4f};  
        float[] f2 = {3.4f,4.5f};  
        Point p1 = new Point(f1);  
        Point p2 = new Point(f2);  
        Rectangle rectangle = new Rectangle(p1, p2);  
        System.out.println(rectangle);  
          
        float[] f_1 = {0f,0f};  
        float[] f_2 = {-2f,2f};  
        float[] f_3 = {3f,3f};  
        float[] f_4 = {2.5f,2.5f};  
        float[] f_5 = {1.5f,1.5f};  
        p1 = new Point(f_1);  
        p2 = new Point(f_2);  
        Point p3 = new Point(f_3);  
        Point p4 = new Point(f_4);  
        Point p5 = new Point(f_5);  
        Rectangle re1 = new Rectangle(p1, p2);  
        Rectangle re2 = new Rectangle(p2, p3);  
        Rectangle re3 = new Rectangle(p4, p3);  
//      Rectangle re4 = new Rectangle(p3, p4);  
        Rectangle re5 = new Rectangle(p5, p4);  
        System.out.println(re1.isIntersection(re2));  
        System.out.println(re2.isIntersection(re3));  
        System.out.println(re3.intersectingArea(re2));  
        System.out.println(re1.intersectingArea(re5));  
    }  
  
    /** 
     * ����Rectangle�ཻ����� 
     * @param rectangle Rectangle 
     * @return float 
     */  
    public float intersectingArea(Rectangle rectangle)   
    {  
        if(! isIntersection(rectangle))  
        {  
            return 0;  
        }  
          
        float ret = 1;  
        for(int i = 0; i < rectangle.getDimension(); i ++)  
        {  
            float l1 = this.low.getFloatCoordinate(i);  
            float h1 = this.high.getFloatCoordinate(i);  
            float l2 = rectangle.low.getFloatCoordinate(i);  
            float h2 = rectangle.high.getFloatCoordinate(i);  
              
            //rectangle1��rectangle2�����  
            if(l1 <= l2 && h1 <= h2)  
            {  
                ret *= (h1 - l1) - (l2 - l1);  
            }else if(l1 >= l2 && h1 >= h2)  
            //rectangle1��rectangle2���ұ�  
            {  
                ret *= (h2 - l2) - (l1 - l2);  
            }else if(l1 >= l2 && h1 <= h2)              
            //rectangle1��rectangle2����  
            {  
                ret *= h1 - l1;  
            }else if(l1 <= l2 && h1 >= h2)      
            //rectangle1����rectangle2  
            {  
                ret *= h2 - l2;  
            }  
        }  
        return ret;  
    }  
      
    /** 
     * @param rectangle 
     * @return �ж�����Rectangle�Ƿ��ཻ 
     */  
    public boolean isIntersection(Rectangle rectangle)  
    {  
        if(rectangle == null)  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
          
        if(rectangle.getDimension() != getDimension())  
        {  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
        }  
          
          
        for(int i = 0; i < getDimension(); i ++)  
        {  
            if(low.getFloatCoordinate(i) > rectangle.high.getFloatCoordinate(i) ||  
                    high.getFloatCoordinate(i) < rectangle.low.getFloatCoordinate(i))  
            {  
                return false;  
            }  
        }  
        return true;  
    }  
  
    /** 
     * @return ����Rectangle��ά�� 
     */  
    private int getDimension()   
    {  
        return low.getDimension();  
    }  
  
    /** 
     * �ж�rectangle�Ƿ񱻰�Χ 
     * @param rectangle 
     * @return 
     */  
    public boolean enclosure(Rectangle rectangle)   
    {  
        if(rectangle == null)  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
          
        if(rectangle.getDimension() != getDimension())  
            throw new IllegalArgumentException("Rectangle dimension is different from current dimension.");  
          
        for(int i = 0; i < getDimension(); i ++)  
        {  
            if(rectangle.low.getFloatCoordinate(i) < low.getFloatCoordinate(i) ||  
                    rectangle.high.getFloatCoordinate(i) > high.getFloatCoordinate(i))  
                return false;  
        }  
        return true;  
    }  
      
//    @Override  
//    public boolean equals(Object obj)   
//    {  
//        if(obj instanceof Rectangle)  
//        {  
//            Rectangle rectangle = (Rectangle) obj;  
//            if(low.equals(rectangle.getLow()) && high.equals(rectangle.getHigh()))  
//                return true;  
//        }  
//        return false;  
//    }  
}  

