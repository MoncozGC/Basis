package com.JadePenG.spider;

public class Product {


    private String id;
    private String url;
    private String price;
    private String name;


    public Product() {
        super();
    }
    public Product(String id, String name, String url, String price) {
        super();
        this.id = id;
        this.url = url;
        this.price = price;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Product [id=" + id + ", url=" + url + ", price=" + price + ", name=" + name + "]";
    }


}
