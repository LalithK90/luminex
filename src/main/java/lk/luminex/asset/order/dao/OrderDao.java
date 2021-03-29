package lk.luminex.asset.order.dao;


import lk.luminex.asset.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository< Order, Integer > {
    List< Order > findByCreatedAtIsBetween(LocalDateTime form, LocalDateTime to);

    List< Order > findByCreatedAtIsBetweenAndCreatedBy(LocalDateTime form, LocalDateTime to, String username);

    Order findFirstByOrderByIdDesc();
}
