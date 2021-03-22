package lk.luminex.asset.project_employee.dao;

import lk.luminex.asset.project_employee.entity.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectEmployeeDao extends JpaRepository< ProjectEmployee, Integer> {
    ProjectEmployee findFirstByOrderByIdDesc();
}
