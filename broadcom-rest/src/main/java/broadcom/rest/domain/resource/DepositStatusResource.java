package broadcom.rest.domain.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositStatusResource {
    private Integer id;
    private String status;
}
