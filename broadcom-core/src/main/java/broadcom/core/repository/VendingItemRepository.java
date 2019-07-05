package broadcom.core.repository;

import broadcom.core.model.VendingItem;

import java.util.List;

public interface VendingItemRepository {
    List<VendingItem> findAll();

    VendingItem save(VendingItem entity);

    VendingItem findFirstByType(String type);

    boolean deleteById(int id);

    long countByType(String type);

}
