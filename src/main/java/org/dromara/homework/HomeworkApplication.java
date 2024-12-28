package org.dromara.homework;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dromara.homework.mapper")
public class HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }
//    @PostConstruct
//    public void init() {
//        System.out.println("======================================");
//        System.out.println("应用已启动成功！");
//        System.out.println("======================================");
//    }
}
