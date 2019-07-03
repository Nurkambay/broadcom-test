package broadcom.entities;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class VendingItemRepository {
    public List<VendingItemEntity> findAll() {
        VendingItemEntity entity = new VendingItemEntity();
        entity.setId(1);
        entity.setType("ZO");
        return new LinkedList<>(Collections.singleton(entity));
    }

    public VendingItemEntity save(VendingItemEntity entity) {
        return null;
    }

    public VendingItemEntity findFirstByType(String type) {
        return null;
    }

    public boolean deleteById(int id) {
        return false;
    }

    public int countByType(String type) {
        return 0;
    }
}
