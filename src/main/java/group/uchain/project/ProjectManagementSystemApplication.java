package group.uchain.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author project
 */
@MapperScan(value = "group.uchain.project.mapper")
@SpringBootApplication
public class ProjectManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);
    }

}
