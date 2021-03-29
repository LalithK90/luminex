package lk.luminex.asset.invoice_ledger.dao;

import lk.luminex.asset.invoice_ledger.entity.OrderLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderLedgerDao extends JpaRepository< OrderLedger, Integer > {
  List< OrderLedger > findByCreatedAtIsBetween(LocalDateTime from, LocalDateTime to);
}
