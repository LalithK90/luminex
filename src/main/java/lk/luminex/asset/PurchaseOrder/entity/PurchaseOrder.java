package lk.luminex.asset.PurchaseOrder.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.luminex.asset.PurchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.luminex.asset.payment.entity.Payment;
import lk.luminex.asset.supplier.entity.Supplier;
import lk.luminex.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("PurchaseOrder")
public class PurchaseOrder extends AuditEntity {

    private String remark;

    @Size(min = 5, message = "Your Company name cannot be accepted")
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

   @ManyToOne
    private Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.PERSIST)
    private List<PurchaseOrderItem> purchaseOrderItems;

    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus purchaseOrderStatus;


    @OneToMany(mappedBy = "purchaseOrder")
    private List<Payment> payments;

}
