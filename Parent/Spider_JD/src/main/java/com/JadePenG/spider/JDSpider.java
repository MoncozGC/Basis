package com.JadePenG.spider;


import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JDSpider {
    // 考虑到ProductDao最好是只被初始化一次，就使用static修饰，在类加载初始化
    private static ProductDao productDao = new ProductDao();

    public static void main(String[] args) throws IOException {
        String indexUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&cid2=653&cid3=655&page=3&click=0";
        //解析url获取html内容
        String html = HttpClientUtils.doGet(indexUrl);
        Document indexDoc = Jsoup.parse(html);
        // 解析所有的商品的id
        Elements pids = indexDoc.select("[data-pid]");
        ArrayList<Product> productList = new ArrayList<Product>();
        for (Element element : pids) {
            //根据id拼接详情页url
            String pid = element.attr("data-pid");
            String pUrl = "https://item.jd.com/"+pid+".html";
            String detailHtml = HttpClientUtils.doGet(pUrl);
            Document detailDoc = Jsoup.parse(detailHtml);
            System.out.println(pUrl);
            // 获取商品的标题、价格、及相关属性span.price:nth-child(2)
            String name = detailDoc.select(".sku-name").get(0).text();
            System.out.println(name);
            //detailDoc中没有price需要在从url中获取
            String pirceUrl = "https://p.3.cn/prices/mgets?skuIds=J_"+pid;
            //返回的结果集是一个json串
            String json = HttpClientUtils.doGet(pirceUrl);
            // 如何解析json串？谷歌提供的Gson
            Gson gson = new Gson();
            List<LinkedTreeMap<String, String>> list = (List<LinkedTreeMap<String, String>>)gson.fromJson(json, List.class);
            //获取list第一个元素
            LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>)list.get(0);
            String price = (String)map.get("p");
            System.out.println(price);
            //-1.00说明商品不止一个，暂时跳过
            if("-1.00".equals(price)){
                continue;
            }
            //封装
            Product product = new Product(pid, name, pUrl, price);
            productList.add(product);

        }


        // 保存数据
        productDao.save(productList);
    }

}
