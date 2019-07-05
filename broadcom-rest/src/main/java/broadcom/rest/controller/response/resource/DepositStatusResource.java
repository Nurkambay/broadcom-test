package broadcom.rest.controller.response.resource;

import lombok.Getter;

@Getter
public class DepositStatusResource extends StatusResource {
    private int id;

    public DepositStatusResource(int id) {
        super("OK");
        this.id = id;
    }
}
