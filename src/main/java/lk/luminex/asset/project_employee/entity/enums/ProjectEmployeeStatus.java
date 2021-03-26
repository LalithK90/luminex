package lk.luminex.asset.project_employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectEmployeeStatus {
  SUP("Supervisor"),
  ENG("Engineer"),
  WORK("Worker"),
  REMOVE("Remove");

  private final String projectEmployeeStatus;

}
