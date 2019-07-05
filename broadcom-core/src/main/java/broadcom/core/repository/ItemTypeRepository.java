package broadcom.core.repository;

import broadcom.core.model.ItemType;

public interface ItemTypeRepository {
    ItemType findByName(String name);
}
