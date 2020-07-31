package my.tree;
import java.util.ArrayList;  
import java.util.List;  
  
public class RTDirNode extends RTNode {//�м�ֵ
	 /** 
     * ���ӽ��
     */  
	    protected List<RTNode> children;  
	      
	    public RTDirNode(RTree rtree, RTNode parent, int level)   
	    {  
	        super(rtree, parent, level);  
	        children = new ArrayList<RTNode>();  
	    }  
	      
	    /** 
	     * @param index 
	     * @return ��Ӧ�����µĺ��ӽ�� 
	     */  
	    public RTNode getChild(int index)  
	    {  
	        return children.get(index);  
	    }  
	  
	    @Override  
	    public RTDataNode chooseLeaf(Rectangle rectangle)   //Ѱ��Ҷ�ӽ��
	    {  
	        int index;  
	          
	        switch (rtree.getTreeType())   
	        {  
	            case Constants.RTREE_LINEAR:  
	                  
	            case Constants.RTREE_QUADRATIC:  
	                  
	            case Constants.RTREE_EXPONENTIAL:  
	                index = findLeastEnlargement(rectangle);  
	                break;  
	            case Constants.RSTAR:  
	                if(level == 1)//���˽��ָ��Ҷ�ڵ�  
	                {  
	                    index = findLeastOverlap(rectangle);  
	                }else{  
	                    index = findLeastEnlargement(rectangle);  
	                }  
	                break;  
	                  
	            default:  
	                throw new IllegalStateException("Invalid tree type.");  
	        }  
	          
	        insertIndex = index;//��¼����·��������  
	          
	        return getChild(index).chooseLeaf(rectangle);  
	    }  
	  
	    /** 
	     * @param rectangle 
	     * @return ������С�ص�����Ľ�������������ص���������ѡ������Rectangle�����������С�ģ������������������ѡ�����������С�� 
	     */  
	    private int findLeastOverlap(Rectangle rectangle) //������Ѳ����������  
	    {  
	        float overlap = Float.POSITIVE_INFINITY; //���� 
	        int sel = -1;  
	          
	        for(int i = 0; i < usedSpace; i ++)  
	        {  
	            RTNode node = getChild(i);//���ӽ��  
	            float ol = 0;  
	              
	            for(int j = 0; j < node.datas.length; j ++)  
	            {  
	                ol += rectangle.intersectingArea(node.datas[j]);  
	            }  
	            if(ol < overlap)  
	            {  
	                overlap = ol;//��¼�ص������С��  
	                sel = i;//��¼�ڼ������ӵ�����  
	            }else if(ol == overlap)//����ص���������ѡ������Rectangle�����������С��,�����������������ѡ�����������С��  
	            {  
	                double area1 = datas[i].getUnionRectangle(rectangle).getArea() - datas[i].getArea();  
	                double area2 = datas[sel].getUnionRectangle(rectangle).getArea() - datas[sel].getArea();  
	                  
	                if(area1 == area2)  
	                {  
	                    sel = (datas[sel].getArea() <= datas[i].getArea()) ? sel : i;  
	                }else{  
	                    sel = (area1 < area2) ? i : sel;  
	                }  
	            }  
	        }  
	        return sel;  
	    }  
	  
	    /** 
	     * @param rectangle 
	     * @return ���������С�Ľ������������������������ѡ�����������С�� 
	     */  
	    private int findLeastEnlargement(Rectangle rectangle)   
	    {  
	        double area = Double.POSITIVE_INFINITY;  //������
	        int sel = -1;  
	          
	        for(int i = 0; i < usedSpace; i ++)  
	        {  
	            double enlargement = datas[i].getUnionRectangle(rectangle).getArea() - datas[i].getArea();  
	            if(enlargement < area)  
	            {  
	                area = enlargement;  
	                sel = i;  
	            }else if(enlargement == area)  
	            {  
	                sel = (datas[sel].getArea() < datas[i].getArea()) ? sel : i;  
	            }  
	        }  
	          
	        return sel;  
	    }  
	  
	    /** 
	     * �����µ�Rectangle��Ӳ����Ҷ�ڵ㿪ʼ���ϵ���RTree��ֱ�����ڵ� 
	     * @param node1 ������Ҫ�����ĺ��ӽ�� 
	     * @param node2  ���ѵĽ�㣬��δ������Ϊnull 
	     */  
	    public void adjustTree(RTNode node1, RTNode node2) //���ϵ�������R��  
	    {  
	        /*�ҵ�ָ��ԭ��������Ŀ������ */
	        datas[insertIndex] = node1.getNodeRectangle();//��node1����ԭ������滻�ɵĽ��   
	        children.set(insertIndex, node1);
	          
	        if(node2 != null)  
	        {  
	            insert(node2);//�����½��  
	              
	        }else if(! isRoot())//��û������ڵ�  
	        {  
	            RTDirNode parent = (RTDirNode) getParent();  
	            parent.adjustTree(this, null);//���ϵ���ֱ�����ڵ�  
	        }  
	    }  
	  
	    /** 
	     * @param node 
	     * @return ��������Ҫ�����򷵻�true 
	     */  
	    protected boolean insert(RTNode node)   
	    {  
	        if(usedSpace < rtree.getNodeCapacity())  
	        {  
	            datas[usedSpace ++] = node.getNodeRectangle();  
	            children.add(node);//�¼ӵ�  
	            node.parent = this;//�¼ӵ�  
	            RTDirNode parent = (RTDirNode) getParent();  
	            if(parent != null)  
	            {  
	                parent.adjustTree(this, null);  
	            }  
	            return false;  
	        }else{//��Ҷ�ӽ����Ҫ����  
	            RTDirNode[] a = splitIndex(node);  
	            RTDirNode n = a[0];  
	            RTDirNode nn = a[1];  
	              
	            if(isRoot())  
	            {  
	                //�½����ڵ㣬������1  
	                RTDirNode newRoot = new RTDirNode(rtree, Constants.NULL, level + 1);  
	                  
	                //����Ҫ��ԭ�����ĺ�����ӵ��������ѵĽ��n��nn�У���ʱn��nn�ĺ��ӽ�㻹Ϊ��  
//	              for(int i = 0; i < n.usedSpace; i ++)  
//	              {  
//	                  n.children.add(this.children.get(index));  
//	              }  
//	                
//	              for(int i = 0; i < nn.usedSpace; i ++)  
//	              {  
//	                  nn.children.add(this.children.get(index));  
//	              }  
	                  
	                //���������ѵĽ��n��nn��ӵ����ڵ�  
	                newRoot.addData(n.getNodeRectangle());  
	                newRoot.addData(nn.getNodeRectangle());  
	                  
	                newRoot.children.add(n);  
	                newRoot.children.add(nn);  
	                  
	                //�����������ѵĽ��n��nn�ĸ��ڵ�  
	                n.parent = newRoot;  
	                nn.parent = newRoot;  
	                  
	                //�������rtree�ĸ��ڵ�  
	                rtree.setRoot(newRoot);//�¼ӵ�  
	            }else {  
	                RTDirNode p = (RTDirNode) getParent();  
	                p.adjustTree(n, nn);  
	            }  
	        }  
	        return true;  
	    }  
	  
	    /** 
	     * ��Ҷ�ӽ��ķ��� 
	     *  
	     * @param node 
	     * @return 
	     */  
	    private RTDirNode[] splitIndex(RTNode node)   
	    {  
	        int[][] group = null;  
	          
	        switch (rtree.getTreeType())   
	        {  
	            case Constants.RTREE_LINEAR:  
	                break;  
	            case Constants.RTREE_QUADRATIC:  
	                group = quadraticSplit(node.getNodeRectangle());  
	                children.add(node);//�¼ӵ�  
	                node.parent = this;//�¼ӵ�  
	                break;  
	            case Constants.RTREE_EXPONENTIAL:  
	                break;  
	            case Constants.RSTAR:  
	                break;  
	            default:  
	                throw new IllegalStateException("Invalid tree type.");  
	        }  
	          
	        RTDirNode index1 = new RTDirNode(rtree, parent, level);  
	        RTDirNode index2 = new RTDirNode(rtree, parent, level);  
	          
	        int[] group1 = group[0];  
	        int[] group2 = group[1];  
	          
	        for(int i = 0; i < group1.length; i ++)  
	        {  
	            //Ϊindex1������ݺͺ���  
	            index1.addData(datas[group1[i]]);  
	            index1.children.add(this.children.get(group1[i]));//�¼ӵ�  
	            //��index1��Ϊ�丸�ڵ�  
	            this.children.get(group1[i]).parent = index1;//�¼ӵ�  
	        }  
	        for(int i = 0; i < group2.length; i ++)  
	        {  
	            index2.addData(datas[group2[i]]);  
	            index2.children.add(this.children.get(group2[i]));//�¼ӵ�  
	            this.children.get(group2[i]).parent = index2;//�¼ӵ�  
	        }  
	          
	        return new RTDirNode[]{index1,index2};  
	    }  
	  
	    @Override  
	    protected RTDataNode findLeaf(Rectangle rectangle)   
	    {  
	        for(int i = 0; i < usedSpace; i ++)  
	        {  
	            if(datas[i].enclosure(rectangle))  
	            {  
	                deleteIndex = i;//��¼����·��  
	                RTDataNode leaf = children.get(i).findLeaf(rectangle);  
	                if(leaf != null)  
	                    return leaf;  
	            }  
	        }  
	        return null;  
	    }  
	  
}
