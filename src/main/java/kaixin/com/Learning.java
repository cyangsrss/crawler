package main.java.kaixin.com;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;

public class Learning implements PageProcessor{
    private Site site=Site.me().setRetryTimes(3).setSleepTime(100);

    public static void main(String[] args) {
        System.out.println(new Date());
        Spider spider = Spider.create(new Learning());
        spider.addUrl("https://www.cnblogs.com/").thread(3).run();
        System.out.println(new Date());
    }

    public void process(Page page) {
        if(!page.getUrl().regex("http://www.cnblogs.com/[a-z 0-9 -]+/p/[0-9]{7}.html").match()){
            //加入满足条件的链接
            page.addTargetRequests(
                    page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class='post_item_body']/h3/a/@href").all());
        }else{
            //获取页面需要的内容
            System.out.println("抓取的内容："+
                    page.getHtml().xpath("//*[@id=\"Header1_HeaderTitle\"]/text()").get()
            );
        }
    }

    public Site getSite() {
        return site;
    }
}
