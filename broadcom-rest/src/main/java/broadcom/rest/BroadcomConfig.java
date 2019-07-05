package broadcom.rest;

import broadcom.core.model.VendingItem;
import broadcom.rest.controller.response.resource.VendingItemResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = { "broadcom.rest", "broadcom.core", "broadcom.storage"}
)
public class BroadcomConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.typeMap(VendingItem.class, VendingItemResource.class)
                .addMappings(m -> m.map(src -> src.getType().getName(), VendingItemResource::setType));

        return mapper;
    }
}

