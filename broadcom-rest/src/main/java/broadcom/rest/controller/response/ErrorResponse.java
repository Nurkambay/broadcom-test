package broadcom.rest.controller.response;

import broadcom.rest.controller.response.resource.StatusResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse extends ResponseEntity<StatusResource> {
    public ErrorResponse(String message, HttpStatus status) {
        super(new StatusResource(message), status);
    }
}
