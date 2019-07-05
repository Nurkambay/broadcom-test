package broadcom.core.repository;

import broadcom.core.model.ItemType;

/**
 * Vending Machine item's type repository
 */
public interface ItemTypeRepository {
    ItemType findByName(String name);
}
