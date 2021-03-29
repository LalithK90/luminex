package lk.luminex.asset.project_employee.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.employee.entity.Employee;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.asset.project_employee.entity.enums.ProjectEmployeeStatus;
import lk.luminex.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("ProjectEmployee")
public class ProjectEmployee extends AuditEntity {

  @Enumerated(EnumType.STRING)
  private LiveDead liveDead;

  @Enumerated( EnumType.STRING)
  private ProjectEmployeeStatus projectEmployeeStatus;

  @ManyToOne
  private Project project;

  @ManyToOne
  private Employee employee;
}
