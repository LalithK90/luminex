package lk.luminex.asset.goodReceivedNote.entity;



import com.fasterxml.jackson.annotation.JsonFilter;
import lk.luminex.asset.PurchaseOrder.entity.PurchaseOrder;
import lk.luminex.asset.goodReceivedNote.entity.Enum.GoodReceivedNoteState;
import lk.luminex.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("GoodReceivedNote")
public class GoodReceivedNote extends AuditEntity {
    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private GoodReceivedNoteState goodReceivedNoteState;

    private String remarks;

}
