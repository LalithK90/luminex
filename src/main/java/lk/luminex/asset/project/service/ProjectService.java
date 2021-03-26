package lk.luminex.asset.project.service;


import lk.luminex.asset.common_asset.model.enums.LiveDead;
import lk.luminex.asset.project.dao.ProjectDao;
import lk.luminex.asset.project.entity.Project;
import lk.luminex.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = "project" )
public class ProjectService implements AbstractService< Project, Integer> {
    private final ProjectDao projectDao;

    @Autowired
    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public List< Project > findAll() {
        return projectDao.findAll().stream()
            .filter(x -> LiveDead.ACTIVE.equals(x.getLiveDead()))
            .collect(Collectors.toList());
    }

    public Project findById(Integer id) {
        return projectDao.getOne(id);
    }

    public Project persist(Project project) {
        if ( project.getId() == null ) {
            project.setLiveDead(LiveDead.ACTIVE);
        }
        return projectDao.save(project);
    }

    public boolean delete(Integer id) {
        Project project = projectDao.getOne(id);
        project.setLiveDead(LiveDead.STOP);
        projectDao.save(project);
        return false;
    }

    public List< Project > search(Project project) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Project > customerExample = Example.of(project, matcher);
        return projectDao.findAll(customerExample);
    }

    public Project lastProject(){
        return projectDao.findFirstByOrderByIdDesc();
    }

}
