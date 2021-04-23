package lk.luminex.asset.order_ledger.service;

import lk.luminex.asset.order_ledger.dao.OrderLedgerDao;
import lk.luminex.asset.order_ledger.entity.OrderLedger;
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

  public OrderLedger findById(Integer id) {
    return orderLedgerDao.getOne(id);
  }
}
