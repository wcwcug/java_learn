package KeywordQuery;


public class LinkList {
	public MyNode mynode = new MyNode(0);//定义一个单链表	
	public MyNode head=mynode;
	public void addNode(int id)//插入节点 
	{
		MyNode node =new MyNode(id);
		mynode.next=node;
		mynode=mynode.next;
	}
	public void displayAllNodes()
	{
		MyNode current= head.next;
		while(current!=null)
		{
			System.out.println(current.id);
			current=current.next;
		}
	}
}
//InvertedIndex b=new InvertedIndex();
//LinkList a=new LinkList();
//a.addNode(1);
//a.addNode(2);
//a.addNode(3);
//a.displayAllNodes();