package lk.luminex.asset.project_order.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.luminex.asset.order_ledger.entity.OrderLedger;
import lk.luminex.asset.project_order.entity.enums.OrderState;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "ProjectOrder" )
public class ProjectOrder extends AuditEntity {

  private String remarks;

  @Column( nullable = false, unique = true )
  private String code;

  @Enumerated(EnumType.STRING)
  private OrderState orderState;

  @ManyToOne
  private Project project;

  @OneToMany( cascade = CascadeType.PERSIST, mappedBy = "projectOrder" )
  private List< OrderLedger > orderLedgers;

}
