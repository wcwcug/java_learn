package webcollector1;

import java.util.Set;
   
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class selenium {
	public static void main(String args[]) throws Exception{
		//设置浏览器驱动的位置，很重要，不然打开的话可能是空白页
		System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
		//System.setProperty("webdriver.gecko.driver", "E:\\基于微博的垂直 搜索\\新建文件夹\\geckodriver.exe");
		//System.setProperty("webdriver.firefox.marionette","E:\\geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "F:\\应用程序\\火狐\\firefox.exe");
		//实例化一个浏览器对象
		
		  /*System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
		    System.setProperty("webdriver.firefox.bin", "F:\\应用程序\\火狐\\firefox.exe");
		    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		    capabilities.setCapability("marionette", true);
		    WebDriver driver = new FirefoxDriver(capabilities);*/
		 WebDriver driver = new FirefoxDriver();
		//网址（手机版的新浪微博，因为这个网站登录不要验证码）
		//String baseUrl = "https://passport.weibo.cn/signin/login";   
		//打开浏览器
		//driver.get("https://www.baidu.com/");
		 driver.get("https://passport.weibo.cn/signin/login");
		//这个时候会打开一个浏览器，连接到你所get的网站

		 

		//因为网站不一定可以马上打开，让进程停一下，否则页面的元素会找不到。
		Thread.sleep(5000000);
		
		//找到名为"loginName"的元素，填写帐号
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("13429823646");

		//找到名为"loginPassword"的元素，填写密码
		driver.findElement(By.id("loginPassword")).clear();
		driver.findElement(By.id("loginPassword")).sendKeys("wang19951030");

		//找到登录按钮，点击
		driver.findElement(By.id("loginAction")).click();

		//然后这个页面就会进入到登录后的界面了

		//因为网站不一定可以马上打开，让进程停一下，否则页面还没有加载出来就进行下一步了。
		Thread.sleep(5000000);
		//一些延伸

		//获取cookies

		Set<Cookie> cookies = driver.manage().getCookies();
		String cookieStr = "";
		for (Cookie cookie : cookies) {
		cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";
		}

		//不过一个WebDriver在登录后自带了Cookies了，直接打开其他地方也是可以的
	}
}
