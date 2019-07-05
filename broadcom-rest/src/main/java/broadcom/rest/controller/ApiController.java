package broadcom.rest.controller;

import broadcom.rest.controller.request.RequestForm;
import broadcom.rest.controller.response.resource.VendingItemResource;
import broadcom.rest.controller.response.DepositResponse;
import broadcom.rest.controller.response.WithdrawResponse;
import broadcom.rest.service.ApiService;
import broadcom.rest.service.ServiceException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService service;
    private final ModelMapper mapper;

    @GetMapping(value = "getlist")
    public List<VendingItemResource> list() {
        return service.list().stream()
                .map(item -> mapper.map(item, VendingItemResource.class))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "deposit")
    public DepositResponse deposit(@RequestBody @Valid RequestForm form) throws ServiceException {
        int id = service.add(form.getType());
        return new DepositResponse(id);
    }

    @PostMapping(value = "withdraw")
    public WithdrawResponse withdraw(@RequestBody @Valid RequestForm form) {
        boolean success = service.remove(form.getType());
        return new WithdrawResponse(success);
    }

}
