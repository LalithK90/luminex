package lk.luminex.asset.order_ledger.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.ledger.entity.Ledger;
import lk.luminex.asset.project_order.entity.ProjectOrder;
import lk.luminex.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("OrderLedger")
public class OrderLedger extends AuditEntity {

    @Column(nullable = false)
    private String quantity;

    @Enumerated( EnumType.STRING)
    private LiveDead liveDead;

    @ManyToOne
    private Ledger ledger;

    @ManyToOne
    private ProjectOrder projectOrder;

}
