package broadcom.storage;

import broadcom.core.model.VendingItem;
import broadcom.core.repository.VendingItemRepository;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class VendingItemRepositoryImpl implements VendingItemRepository {

    private static AtomicInteger idQueue = new AtomicInteger();
    private static Map<Integer, VendingItem> itemMap = new HashMap<>();

//    @PostConstruct
//    public void init() {
//        for (ItemType itemType : ItemTypeRepositoryImpl.itemTypes.values()) {
//            for (int i = 0; i < 10; i++) {
//                int id = idQueue.incrementAndGet();
//                itemMap.put(id, new VendingItem(id, itemType));
//            }
//        }
//    }

    @Override
    @Synchronized("itemMap")
    public List<VendingItem> findAll() {
        List<VendingItem> result = new LinkedList<>(itemMap.values());
        result.sort(Comparator.comparing(s -> s.getType().getName()));
        return result;
    }

    @Override
    @Synchronized("itemMap")
    public VendingItem save(VendingItem entity) {
        if (entity.getId() == null) {
            entity.setId(idQueue.incrementAndGet());
        }
        itemMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    @Synchronized("itemMap")
    public VendingItem findFirstByTypeName(String typeName) {
        return itemMap.values().stream()
                .filter(i -> i.getType().getName().equals(typeName))
                .findFirst()
                .orElse(null);
    }

    @Override
    @Synchronized("itemMap")
    public boolean deleteById(int id) {
        return (itemMap.remove(id) != null);
    }

    @Override
    @Synchronized("itemMap")
    public long countByTypeName(String typeName) {
        return itemMap.values().stream()
                .filter(i -> i.getType().getName().equals(typeName))
                .count();
    }
}
