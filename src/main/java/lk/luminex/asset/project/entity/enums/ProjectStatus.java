package lk.luminex.asset.project.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStatus {
  ACTIVE("Active"),
  TCLOSED("Temporally Closed"),
  CLO("Closed");

  private final String projectStatus;
}
