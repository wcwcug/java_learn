package KeywordQuery;


public class LinkList {
	public MyNode mynode = new MyNode(0);//����һ��������	
	public MyNode head=mynode;
	public void addNode(int id)//����ڵ� 
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