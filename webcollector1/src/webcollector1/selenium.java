package webcollector1;

import java.util.Set;
   
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class selenium {
	public static void main(String args[]) throws Exception{
		//���������������λ�ã�����Ҫ����Ȼ�򿪵Ļ������ǿհ�ҳ
		System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
		//System.setProperty("webdriver.gecko.driver", "E:\\����΢���Ĵ�ֱ ����\\�½��ļ���\\geckodriver.exe");
		//System.setProperty("webdriver.firefox.marionette","E:\\geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "F:\\Ӧ�ó���\\���\\firefox.exe");
		//ʵ����һ�����������
		
		  /*System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
		    System.setProperty("webdriver.firefox.bin", "F:\\Ӧ�ó���\\���\\firefox.exe");
		    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		    capabilities.setCapability("marionette", true);
		    WebDriver driver = new FirefoxDriver(capabilities);*/
		 WebDriver driver = new FirefoxDriver();
		//��ַ���ֻ��������΢������Ϊ�����վ��¼��Ҫ��֤�룩
		//String baseUrl = "https://passport.weibo.cn/signin/login";   
		//�������
		//driver.get("https://www.baidu.com/");
		 driver.get("https://passport.weibo.cn/signin/login");
		//���ʱ����һ������������ӵ�����get����վ

		 

		//��Ϊ��վ��һ���������ϴ򿪣��ý���ͣһ�£�����ҳ���Ԫ�ػ��Ҳ�����
		Thread.sleep(5000000);
		
		//�ҵ���Ϊ"loginName"��Ԫ�أ���д�ʺ�
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("13429823646");

		//�ҵ���Ϊ"loginPassword"��Ԫ�أ���д����
		driver.findElement(By.id("loginPassword")).clear();
		driver.findElement(By.id("loginPassword")).sendKeys("wang19951030");

		//�ҵ���¼��ť�����
		driver.findElement(By.id("loginAction")).click();

		//Ȼ�����ҳ��ͻ���뵽��¼��Ľ�����

		//��Ϊ��վ��һ���������ϴ򿪣��ý���ͣһ�£�����ҳ�滹û�м��س����ͽ�����һ���ˡ�
		Thread.sleep(5000000);
		//һЩ����

		//��ȡcookies

		Set<Cookie> cookies = driver.manage().getCookies();
		String cookieStr = "";
		for (Cookie cookie : cookies) {
		cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";
		}

		//����һ��WebDriver�ڵ�¼���Դ���Cookies�ˣ�ֱ�Ӵ������ط�Ҳ�ǿ��Ե�
	}
}
