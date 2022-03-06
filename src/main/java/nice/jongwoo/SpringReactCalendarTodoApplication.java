package nice.jongwoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringReactCalendarTodoApplication extends SpringBootServletInitializer {
//public class SpringReactCalendarTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringReactCalendarTodoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringReactCalendarTodoApplication.class);
    }
}
