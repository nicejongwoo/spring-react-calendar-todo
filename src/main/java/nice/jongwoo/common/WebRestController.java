package nice.jongwoo.common;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@AllArgsConstructor
@RestController
public class WebRestController {

    private Environment environment;

    @GetMapping("/active-profile")
    public String getActiveProfile() {
        return Arrays.stream(environment.getActiveProfiles())
            .findFirst()
            .orElse("");
    }

}
