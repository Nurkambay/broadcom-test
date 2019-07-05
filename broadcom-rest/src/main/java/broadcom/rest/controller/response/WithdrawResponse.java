package broadcom.rest.controller.response;

import broadcom.rest.controller.response.resource.StatusResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WithdrawResponse extends ResponseEntity<StatusResource> {
    public WithdrawResponse(boolean success) {
        super(new StatusResource(success ? "OK" : "N/A"), HttpStatus.OK);
    }
}
