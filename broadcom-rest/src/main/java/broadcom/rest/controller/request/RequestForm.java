package broadcom.rest.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestForm {
    @NotNull
    private String type;
}
