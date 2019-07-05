package broadcom.rest.service;

import broadcom.core.CoreException;
import broadcom.core.VendingMachine;
import broadcom.core.model.VendingItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test only adapter functionality
 * Business logic's tests are implemented in VendingMachineTest class
 */
class ApiServiceTest {

    private ApiService apiService;
    private VendingMachine vendingMachine;

    @BeforeEach
    void init() {
        vendingMachine = mock(VendingMachine.class);
        apiService = new ApiService(vendingMachine);
    }

    @Test
    void testList() {
        when(vendingMachine.list()).thenReturn(Arrays.asList(new VendingItem(), new VendingItem(), new VendingItem()));
        assertSame(apiService.list().size(), 3);
    }

    @Test
    void testAdd() throws ServiceException {
        apiService.add(anyString());
    }

    @Test
    void testAddException() throws CoreException {
        when(vendingMachine.add(anyString())).thenThrow(new CoreException("Test"));
        assertThrows(ServiceException.class, () -> apiService.add(anyString()));
    }

    @Test
    void testRemoveExisting() {
        when(vendingMachine.remove("Existing")).thenReturn(true);
        assertSame(apiService.remove("Existing"), true);
    }

    @Test
    void testRemoveNA() {
        when(vendingMachine.remove("NA")).thenReturn(false);
        assertSame(apiService.remove("NA"), false);
    }

}
