package broadcom.storage;

import broadcom.core.model.ItemType;
import broadcom.core.repository.ItemTypeRepository;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class ItemTypeRepositoryImpl implements ItemTypeRepository {

    static Map<String, ItemType> itemTypes = new HashMap<String, ItemType>() {
        {
            put("Soda", new ItemType("Soda"));
            put("Candy", new ItemType("Candy"));
            put("Toy", new ItemType("Toy"));
        }
    };

    @Override
    @Synchronized
    public ItemType findByName(String name) {
        return itemTypes.get(name);
    }
}
