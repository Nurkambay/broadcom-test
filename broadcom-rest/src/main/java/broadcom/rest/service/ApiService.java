package broadcom.rest.service;

import broadcom.core.CoreException;
import broadcom.core.VendingMachine;
import broadcom.core.model.VendingItem;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final VendingMachine vendingMachine;

    @Synchronized("vendingMachine")
    public List<VendingItem> list() {
        return vendingMachine.list();
    }

    @Synchronized("vendingMachine")
    public int add(String type) throws ServiceException {
        try {
            return vendingMachine.add(type);
        } catch (CoreException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Synchronized("vendingMachine")
    public boolean remove(String type) {
        return vendingMachine.remove(type);
    }
}
