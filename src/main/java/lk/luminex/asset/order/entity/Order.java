package lk.luminex.asset.order.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.invoice_ledger.entity.OrderLedger;
import lk.luminex.asset.order.entity.enums.OrderState;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Order" )
public class Order extends AuditEntity {

  private String remarks;

  @Column( nullable = false, unique = true )
  private String code;

  @Column( nullable = false, precision = 10, scale = 2 )
  private BigDecimal totalPrice;

  @Column( nullable = false, precision = 10, scale = 2 )
  private BigDecimal totalAmount;

  @Enumerated(EnumType.STRING)
  private OrderState orderState;


  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @ManyToOne
  private Project project;

  @OneToMany( cascade = CascadeType.PERSIST, mappedBy = "invoice" )
  private List< OrderLedger > orderLedgers;


}
