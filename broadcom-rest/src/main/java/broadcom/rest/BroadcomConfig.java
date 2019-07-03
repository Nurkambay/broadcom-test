package broadcom.rest;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = { "broadcom.rest", "broadcom.entities"}
)
public class BroadcomConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

