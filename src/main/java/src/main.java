package src;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import src.config.filter.GlobalExceptionFilterTmp;

@SpringBootApplication
@EnableWebMvc
@EnableAsync
public class main {

    public static void main(String[] args) {
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
    @Bean
    public GlobalExceptionFilterTmp globalExceptionFilter() {
        return new GlobalExceptionFilterTmp();
    }
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}




