package lk.luminex.asset.project_employee.dao;

import lk.luminex.asset.project.entity.Project;
import lk.luminex.asset.project_employee.entity.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectEmployeeDao extends JpaRepository< ProjectEmployee, Integer> {
    ProjectEmployee findFirstByOrderByIdDesc();
  List< ProjectEmployee> findByProject(Project project);
}
