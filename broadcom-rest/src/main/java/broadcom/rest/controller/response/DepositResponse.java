package broadcom.rest.controller.response;

import broadcom.rest.controller.response.resource.DepositStatusResource;
import broadcom.rest.controller.response.resource.StatusResource;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Value
public class DepositResponse extends ResponseEntity<StatusResource> {
    public DepositResponse(int id) {
        super(new DepositStatusResource(id), HttpStatus.OK);
    }
}
