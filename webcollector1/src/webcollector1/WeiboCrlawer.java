package webcollector1;
import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;  
import cn.edu.hfut.dmic.webcollector.model.Links;  
import cn.edu.hfut.dmic.webcollector.model.Page;  
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.weiboapi.WeiboCN;

import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;  
  
/** 
 * 
 * @author hu 
 */  
public class WeiboCrlawer extends DeepCrawler{  
  
    public WeiboCrlawer(String crawlPath) throws Exception {  
        super(crawlPath);  
        /*��ȡ����΢����cookie���˺�������������ʽ���䣬��ʹ��С��*/  
        String cookie=WeiboCN.getSinaCookie("����С��ү", "wang19951030");  
        HttpRequesterImpl myRequester=(HttpRequesterImpl) this.getHttpRequester();  
        myRequester.setCookie(cookie);  
    }  
  
    @Override  
    public Links visitAndGetNextLinks(Page page) {  
        /*��ȡ΢��*/  
        Elements weibos=page.getDoc().select("div.c");  
        for(Element weibo:weibos){  
            System.out.println(weibo.text());  
        }  
        /*���Ҫ��ȡ���ۣ�������Գ�ȡ����ҳ���URL������*/  
        return null;  
    }  
      
    public static void main(String[] args) throws Exception{  
        WeiboCrlawer crawler=new WeiboCrlawer("/home/hu/data/weibo");  
        crawler.setThreads(3);  
        /*��ĳ��΢��ǰ5ҳ������ȡ*/  
        for(int i=0;i<5;i++){  
            crawler.addSeed("http://weibo.cn/zhouhongyi?vt=4&page="+i);  
        }  
        crawler.start(1);  
    }  
      
} 