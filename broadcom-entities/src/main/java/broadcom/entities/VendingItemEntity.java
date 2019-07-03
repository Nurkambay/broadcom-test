package broadcom.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendingItemEntity {
    private int id;
    private String type;
    private BigDecimal price;
}
