package broadcom.core.repository;

import broadcom.core.model.VendingItem;

import java.util.List;

/**
 * Vending Machine items repository
 */
public interface VendingItemRepository {
    List<VendingItem> findAll();

    VendingItem save(VendingItem entity);

    VendingItem findFirstByTypeName(String typeName);

    boolean deleteById(int id);

    long countByTypeName(String typeName);

}
