package com.JadePenG.spider;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProductDao extends JdbcTemplate {

    public ProductDao() {
        // JdbcTemplate 需要一个数据库连接池
        // 初始化一个数据库连接池，使用c3p0
        // 3个人开发，3000万美元
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // username,password,驱动类，数据库连接 spider
        dataSource.setUser("root");
        dataSource.setPassword("0712");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/demo?characterEncoding=utf-8");
        // 将datasource传给JdbcTemplate
        setDataSource(dataSource);

    }


    public void save(Product product) {
        String sql = "INSERT INTO `demo`.`jd_product` (`id`, `name`, `price`, `url`) VALUES (?,?,?,?)";
        update(sql, product.getId(), product.getName(), product.getPrice(), product.getUrl());
    }


    public void save(List<Product> productList) {
        for (Product product : productList) {
            save(product);
        }
    }





}
