package broadcom.rest.service;

import broadcom.entities.VendingItemEntity;
import broadcom.entities.VendingItemFactory;
import broadcom.entities.VendingItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendingItemService {
    private final VendingItemRepository repository;
    private final VendingItemFactory itemFactory;

    @Value("${vending.capacity}")
    private int maxCapacity;

    @Synchronized
    public List<VendingItemEntity> list() {
        return repository.findAll();
    }

    @Synchronized
    public int add(String type) throws ServiceException {
        int count = repository.countByType(type);
        if (count >= maxCapacity) {
            throw new ServiceException("Out of vending machine capacity");
        }
        VendingItemEntity entity = itemFactory.createByType(type);
        return Optional.ofNullable(entity).map(e -> repository.save(entity))
                .map(VendingItemEntity::getId)
                .orElseThrow(() -> new ServiceException("Cannot deposit item with unknown type: " + type));
    }

    @Synchronized
    public boolean remove(String type) {
        VendingItemEntity entity = repository.findFirstByType(type);
        return Optional.ofNullable(entity)
                .map((e) -> repository.deleteById(e.getId()))
                .orElse(false);
    }
}
