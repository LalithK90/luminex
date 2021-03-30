package lk.luminex.asset.project_order.dao;


import lk.luminex.asset.project_order.entity.ProjectOrder;
import lk.luminex.asset.project_order.entity.enums.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectOrderDao extends JpaRepository< ProjectOrder, Integer > {
    List< ProjectOrder > findByCreatedAtIsBetween(LocalDateTime form, LocalDateTime to);

    List< ProjectOrder > findByCreatedAtIsBetweenAndCreatedBy(LocalDateTime form, LocalDateTime to, String username);

    ProjectOrder findFirstByOrderByIdDesc();

  List< ProjectOrder> findByCreatedAtIsBetweenAndOrderState(LocalDateTime from, LocalDateTime to, OrderState orderState);
}
