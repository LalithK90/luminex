package lk.luminex.asset.project_order.service;

import lk.luminex.asset.project_order.dao.ProjectOrderDao;
import lk.luminex.asset.project_order.entity.ProjectOrder;
import lk.luminex.asset.project_order.entity.enums.OrderState;
import lk.luminex.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectOrderService implements AbstractService< ProjectOrder, Integer > {
  private final ProjectOrderDao projectOrderDao;

  public ProjectOrderService(ProjectOrderDao projectOrderDao) {
    this.projectOrderDao = projectOrderDao;
  }


  public List< ProjectOrder > findAll() {
    return projectOrderDao.findAll();
  }

  public ProjectOrder findById(Integer id) {
    return projectOrderDao.getOne(id);
  }

  public ProjectOrder persist(ProjectOrder projectOrder) {
    return projectOrderDao.save(projectOrder);
  }

  public boolean delete(Integer id) {
    ProjectOrder projectOrder = projectOrderDao.getOne(id);
    projectOrder.setOrderState(OrderState.CANCELED);
    projectOrderDao.save(projectOrder);
    return false;
  }

  public List< ProjectOrder > search(ProjectOrder projectOrder) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< ProjectOrder > orderExample = Example.of(projectOrder, matcher);
    return projectOrderDao.findAll(orderExample);

  }

  public List< ProjectOrder > findByCreatedAtIsBetween(LocalDateTime from, LocalDateTime to) {
    return projectOrderDao.findByCreatedAtIsBetween(from, to);
  }

  public ProjectOrder findByLastOrder() {
    return projectOrderDao.findFirstByOrderByIdDesc();
  }

  public List< ProjectOrder > findByCreatedAtIsBetweenAndCreatedBy(LocalDateTime from, LocalDateTime to, String userName) {
    return projectOrderDao.findByCreatedAtIsBetweenAndCreatedBy(from, to, userName);
  }

  public List< ProjectOrder> findByCreatedAtIsBetweenAndOrderState(LocalDateTime from, LocalDateTime to, OrderState orderState) {
  return projectOrderDao.findByCreatedAtIsBetweenAndOrderState(from,to,orderState);
  }
}
