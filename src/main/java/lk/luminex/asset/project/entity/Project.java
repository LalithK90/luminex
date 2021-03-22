package lk.luminex.asset.project.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.common_asset.model.enums.Title;
import lk.luminex.asset.employee.entity.Employee;
import lk.luminex.asset.project_employee.entity.ProjectEmployee;
import lk.luminex.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Project")
public class Project extends AuditEntity {

    @Size(min = 5, message = "Your name cannot be accepted")
    private String name;

    @Size(max = 10, min = 9, message = "Mobile number length should be contained 10 and 9")
    @Column(unique = true)
    private String phone;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255)
    private String address;

    @Column(unique = true)
    private String code; // ex. {yearLastTwo}{less than ten thousand}

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private LiveDead liveDead;

    @OneToMany(mappedBy = "project")
    private List< ProjectEmployee > projectEmployees;

}
