package broadcom.entities;

import org.springframework.stereotype.Component;

@Component
public class VendingItemFactory {
    public VendingItemEntity createByType(String type) {
        return new VendingItemEntity();
    }
}
