package broadcom.rest.controller;

import broadcom.rest.domain.form.DepositForm;
import broadcom.rest.domain.resource.DepositStatusResource;
import broadcom.rest.domain.resource.VendingItemResource;
import broadcom.rest.service.ServiceException;
import broadcom.rest.service.VendingItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class VendingItemController {

    private final VendingItemService service;
    private final ModelMapper mapper;

    @GetMapping(value = "getlist")
    public List<VendingItemResource> list() {
        return service.list().stream()
                .map(item -> mapper.map(item, VendingItemResource.class))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "deposit")
    public ResponseEntity<DepositStatusResource> deposit(@RequestBody DepositForm form) {
        try {
            int id = service.add(form.getType());
            return new ResponseEntity<>(new DepositStatusResource(id, "OK"), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new DepositStatusResource(null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
