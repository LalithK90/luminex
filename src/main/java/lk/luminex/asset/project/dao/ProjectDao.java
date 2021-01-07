package lk.luminex.asset.project.dao;

import lk.luminex.asset.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends JpaRepository< Project, Integer> {
    Project findFirstByOrderByIdDesc();
}
