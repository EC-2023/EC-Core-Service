package src;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import src.config.filter.GlobalExceptionFilter;
import src.config.mapper.AutoMappers;

@SpringBootApplication
@EnableWebMvc
public class main {
    @Bean
    public GlobalExceptionFilter globalExceptionFilter() {
        return new GlobalExceptionFilter();
    }
    public static void main(String[] args) {
        AutoMappers.create();
        SpringApplication.run(main.class, args);
        System.out.println("""
                --------------------------------------------------------------------------------------------------------------------------------------------------------
                """);
        System.out.println("""
                🚀 Server ready at http://localhost:8080
                """);
        System.out.println("""
                🚀 Api doc ready at http://localhost:8080/swagger-ui/index.html
                """);
    }
}




