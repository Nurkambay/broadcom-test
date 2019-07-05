package broadcom.core;

import broadcom.core.model.ItemType;
import broadcom.core.model.VendingItem;
import broadcom.core.repository.ItemTypeRepository;
import broadcom.core.repository.MachinePropertiesRepository;
import broadcom.core.repository.VendingItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Main core class with business logic of vending machine
 * Class is not thread safe
 */
@Service
@RequiredArgsConstructor
public class VendingMachine {
    private final VendingItemRepository itemRepository;
    private final MachinePropertiesRepository configRepository;
    private final ItemTypeRepository typeRepository;

    /**
     * List all vending machine items sorted by type
     * @return list of vending items
     */
    public List<VendingItem> list() {
        return itemRepository.findAll();
    }

    /**
     * Add new item of given type to vending machine
     * @param typeName name of the item type
     * @return added vending item id
     * @throws CoreException when trying to add an item to full vending machine
     */
    public int add(String typeName) throws CoreException {
        long count = itemRepository.countByTypeName(typeName);
        if (count >= configRepository.get().getCapacity()) {
            throw new CoreException("Out of vending machine capacity");
        }

        ItemType itemType = typeRepository.findByName(typeName);
        if (itemType == null) {
            throw new CoreException("Item type '" + typeName + "' not allowed");
        }

        VendingItem entity = VendingItem
                .builder()
                .type(itemType)
                .build();

        return Optional.ofNullable(entity).map(e -> itemRepository.save(entity))
                .map(VendingItem::getId)
                .orElseThrow(() -> new CoreException("Cannot deposit item with unknown type: " + typeName));
    }

    /**
     * Remove item by item type name
     * @param typeName name of the item to remove
     * @return @true if item was removed or @false if there was no such item in the repository
     */
    public boolean remove(String typeName) {
        VendingItem entity = itemRepository.findFirstByTypeName(typeName);
        return Optional.ofNullable(entity)
                .map((e) -> itemRepository.deleteById(e.getId()))
                .orElse(false);
    }
}
