package lk.luminex.asset.project_order.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderState {
  APPROVED("Approved"),
  CANCELED("Canceled"),
  PENDING("Approved");

  private final String orderState;
}
