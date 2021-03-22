package lk.luminex.asset.project_employee.service;

import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.project_employee.dao.ProjectEmployeeDao;
import lk.luminex.asset.project_employee.entity.ProjectEmployee;
import lk.luminex.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = "customer" )
public class ProjectEmployeeService implements AbstractService< ProjectEmployee, Integer> {
    private final ProjectEmployeeDao projectEmployeeDao;

    @Autowired
    public ProjectEmployeeService(ProjectEmployeeDao projectEmployeeDao) {
        this.projectEmployeeDao = projectEmployeeDao;
    }

    public List< ProjectEmployee > findAll() {
        return projectEmployeeDao.findAll().stream()
            .filter(x -> LiveDead.ACTIVE.equals(x.getLiveDead()))
            .collect(Collectors.toList());
    }

    public ProjectEmployee findById(Integer id) {
        return projectEmployeeDao.getOne(id);
    }

    public ProjectEmployee persist(ProjectEmployee projectEmployee) {
        if ( projectEmployee.getId() == null ) {
            projectEmployee.setLiveDead(LiveDead.ACTIVE);
        }
        return projectEmployeeDao.save(projectEmployee);
    }

    public boolean delete(Integer id) {
        ProjectEmployee projectEmployee = projectEmployeeDao.getOne(id);
        projectEmployee.setLiveDead(LiveDead.STOP);
        projectEmployeeDao.save(projectEmployee);
        return false;
    }

    public List< ProjectEmployee > search(ProjectEmployee projectEmployee) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< ProjectEmployee > customerExample = Example.of(projectEmployee, matcher);
        return projectEmployeeDao.findAll(customerExample);
    }

    public ProjectEmployee lastCustomer(){
        return projectEmployeeDao.findFirstByOrderByIdDesc();
    }
}
