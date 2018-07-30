package cn.caizhaoke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhaoke_cai@163.com
 * @date 2018/7/30
 * @description springboot main 入口
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"cn.caizhaoke.**"})
public class PayListApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayListApplication.class, args);
    }
}
