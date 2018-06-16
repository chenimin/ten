package ci.ten;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ci.ten.dao")
public class TenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenApplication.class, args);
    }
}
