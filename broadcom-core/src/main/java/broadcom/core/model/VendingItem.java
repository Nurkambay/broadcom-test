package broadcom.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Vending machine item
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendingItem {
    private Integer id;
    private ItemType type;
}
