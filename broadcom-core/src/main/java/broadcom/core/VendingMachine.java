package broadcom.core;

import broadcom.core.model.ItemType;
import broadcom.core.model.VendingItem;
import broadcom.core.repository.ItemTypeRepository;
import broadcom.core.repository.MachinePropertiesRepository;
import broadcom.core.repository.VendingItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendingMachine {
    private final VendingItemRepository itemRepository;
    private final MachinePropertiesRepository configRepository;
    private final ItemTypeRepository typeRepository;

    @Synchronized
    public List<VendingItem> list() {
        return itemRepository.findAll();
    }

    @Synchronized
    public int add(String type) throws CoreException {
        long count = itemRepository.countByType(type);
        if (count >= configRepository.get().getCapacity()) {
            throw new CoreException("Out of vending machine capacity");
        }

        ItemType itemType = typeRepository.findByName(type);
        if (itemType == null) {
            throw new CoreException("Item type '" + type + "' not allowed");
        }

        VendingItem entity = VendingItem
                .builder()
                .type(itemType)
                .build();

        return Optional.ofNullable(entity).map(e -> itemRepository.save(entity))
                .map(VendingItem::getId)
                .orElseThrow(() -> new CoreException("Cannot deposit item with unknown type: " + type));
    }

    @Synchronized
    public boolean remove(String type) {
        VendingItem entity = itemRepository.findFirstByType(type);
        return Optional.ofNullable(entity)
                .map((e) -> itemRepository.deleteById(e.getId()))
                .orElse(false);
    }
}
