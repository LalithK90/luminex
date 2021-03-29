package lk.luminex.asset.purchase_order.common_model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseOrderItemLedger {
    private int itemId;
    private String itemName;
    private String rop;
    private BigDecimal price;
    private String availableQuantity;
}
