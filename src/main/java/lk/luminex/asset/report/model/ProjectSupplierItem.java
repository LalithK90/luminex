package lk.luminex.asset.report.model;

import lk.luminex.asset.item.entity.Item;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.asset.supplier_item.entity.SupplierItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSupplierItem {
  private Project project;
  private List< Item > items;
  private BigDecimal amount;
}
