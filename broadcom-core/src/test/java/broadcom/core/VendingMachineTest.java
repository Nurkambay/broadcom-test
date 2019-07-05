package broadcom.core;

import broadcom.core.model.ItemType;
import broadcom.core.model.MachineProperties;
import broadcom.core.model.VendingItem;
import broadcom.core.repository.ItemTypeRepository;
import broadcom.core.repository.MachinePropertiesRepository;
import broadcom.core.repository.VendingItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VendingMachineTest {

    private VendingMachine vendingMachine;
    private VendingItemRepository vendingItemRepository;
    private MachinePropertiesRepository propertiesRepository;

    @BeforeEach
    void init() {
        vendingItemRepository = mock(VendingItemRepository.class);

        propertiesRepository = mock(MachinePropertiesRepository.class);
        when(propertiesRepository.get()).thenReturn(new MachineProperties(2));

        ItemTypeRepository itemTypeRepository = mock(ItemTypeRepository.class);
        vendingMachine = new VendingMachine(vendingItemRepository, propertiesRepository, itemTypeRepository);

        ItemType itemType = new ItemType("Soda");
        when(itemTypeRepository.findByName("Soda")).thenReturn(itemType);
        when(vendingItemRepository.save(any())).thenReturn(new VendingItem(10, itemType));
    }

    @Test
    void testAddNotAllowed() {
        assertThrows(CoreException.class, () -> vendingMachine.add("NOT ALLOWED ITEM TYPE"));
    }

    @Test
    void testAddAllowed() throws CoreException {
        assertSame(vendingMachine.add("Soda"), 10);
    }

    @Test
    void testAddOutOfCapacity() {
        when(propertiesRepository.get()).thenReturn(new MachineProperties(0));
        assertThrows(CoreException.class, () -> vendingMachine.add("Soda"));
    }

    @Test
    void testWithdrawExisting() {
        when(vendingItemRepository.findFirstByTypeName(any())).thenReturn(new VendingItem(1, any()));
        when(vendingItemRepository.deleteById(1)).thenReturn(true);
        assertSame(vendingMachine.remove("Soda"), true);
    }

    @Test
    void testWithdrawNA() {
        when(vendingItemRepository.findFirstByTypeName(any())).thenReturn(null);
        when(vendingItemRepository.deleteById(1)).thenReturn(true);
        assertSame(vendingMachine.remove("Soda"), false);
    }

    @Test
    void testList() {
        when(vendingItemRepository.findAll()).thenReturn(Arrays.asList(new VendingItem(), new VendingItem()));
        List<VendingItem> list = vendingMachine.list();
        assertSame(list.size(), 2);
    }
}
