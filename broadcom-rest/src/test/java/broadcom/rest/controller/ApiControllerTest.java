package broadcom.rest.controller;

import broadcom.core.model.ItemType;
import broadcom.core.model.VendingItem;
import broadcom.rest.BroadcomConfig;
import broadcom.rest.controller.request.RequestForm;
import broadcom.rest.controller.response.DepositResponse;
import broadcom.rest.controller.response.WithdrawResponse;
import broadcom.rest.controller.response.resource.DepositStatusResource;
import broadcom.rest.controller.response.resource.VendingItemResource;
import broadcom.rest.service.ApiService;
import broadcom.rest.service.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ApiController unit test
 * Integration tests are implemented in the IntegrationTests class
 */
class ApiControllerTest {
    private ApiController apiController;
    private ApiService apiService;

    @BeforeEach
    void init() {
        apiService = mock(ApiService.class);
        apiController = new ApiController(apiService, new BroadcomConfig().modelMapper());
    }

    @Test
    void testGetlist() {
        ItemType soda = new ItemType("Soda");

        when(apiService.list()).thenReturn(Arrays.asList(new VendingItem(1, soda), new VendingItem(2, soda)));
        List<VendingItemResource> result = apiController.list();
        assertSame(result.size(), 2);

        int id = 1;
        for (VendingItemResource resource : result) {
            assertSame(resource.getId(), id);
            assertSame(resource.getType(), soda.getName());
            id++;
        }
    }

    @Test
    void testDeposit() throws ServiceException {
        when(apiService.add(any())).thenReturn(1);

        RequestForm requestForm = new RequestForm();
        requestForm.setType("Soda");

        DepositResponse response = apiController.deposit(requestForm);
        assertSame(Objects.requireNonNull(response.getBody()).getStatus(), "OK");
        assertSame(((DepositStatusResource)response.getBody()).getId(), 1);
    }

    @Test
    void testWithdraw() {
        RequestForm requestForm = new RequestForm();
        requestForm.setType("Soda");

        when(apiService.remove(any())).thenReturn(false);
        WithdrawResponse response = apiController.withdraw(requestForm);
        assertSame(Objects.requireNonNull(response.getBody()).getStatus(), "N/A");

        when(apiService.remove(any())).thenReturn(true);
        response = apiController.withdraw(requestForm);
        assertSame(Objects.requireNonNull(response.getBody()).getStatus(), "OK");
    }

}
