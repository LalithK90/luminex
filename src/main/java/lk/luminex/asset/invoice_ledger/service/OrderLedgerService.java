package lk.luminex.asset.invoice_ledger.service;

import lk.luminex.asset.invoice_ledger.dao.OrderLedgerDao;
import lk.luminex.asset.invoice_ledger.entity.OrderLedger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderLedgerService {
private final OrderLedgerDao orderLedgerDao;

  public OrderLedgerService(OrderLedgerDao orderLedgerDao) {
    this.orderLedgerDao = orderLedgerDao;
  }

  public List< OrderLedger > findByCreatedAtIsBetween(LocalDateTime from, LocalDateTime to) {
  return orderLedgerDao.findByCreatedAtIsBetween(from, to);
  }
}
