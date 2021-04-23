package lk.luminex.asset.ledger.dao;


import lk.luminex.asset.item.entity.Item;
import lk.luminex.asset.ledger.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LedgerDao extends JpaRepository< Ledger, Integer> {
    Ledger findByItem(Item item);

    Ledger findByItem_id(int id);

    List< Ledger > findByCreatedAtBetween(LocalDateTime form, LocalDateTime to);

//    @Query("SELECT u FROM User u WHERE u.status = 1")
//    Collection<User> findAllActiveUsers();
}
