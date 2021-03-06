package com.neptune.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h2>启动类</h2>
 * <h3>{@code SpringBootApplication} 自动全包扫描注解</h3>
 */
@SpringBootApplication
@MapperScan(value = "com.neptune.shop.core.dao")
public class CampusShopManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusShopManagementApplication.class, args);
    }

}
