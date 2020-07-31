package my.tree;

public class LowPoint {
	/*坐标点*/
	float a;
	float b;
	/*时间 */
	int time;
	/*编号*/
	int number;
	/*方向*/
	int speed;
	
	public LowPoint(float x,float y,int numbers,int times,int speeds)//构造函数
	{
		number=numbers;
		a=x;
		b=y;		
		time=times;
		speed=speeds;
	}
}
