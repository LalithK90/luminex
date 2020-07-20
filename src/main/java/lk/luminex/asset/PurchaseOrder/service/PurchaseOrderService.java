package lk.luminex.asset.PurchaseOrder.service;

import lk.luminex.asset.PurchaseOrder.dao.PurchaseOrderDao;
import lk.luminex.asset.PurchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.luminex.asset.PurchaseOrder.entity.PurchaseOrder;
import lk.luminex.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@CacheConfig(cacheNames = "purchaseOrder")
public class PurchaseOrderService implements AbstractService<PurchaseOrder, Integer> {
    private final PurchaseOrderDao purchaseOrderDao;

    @Autowired
    public PurchaseOrderService(PurchaseOrderDao purchaseOrderDao) {
        this.purchaseOrderDao = purchaseOrderDao;
    }

    public List<PurchaseOrder> findAll() {
        return purchaseOrderDao.findAll();
    }

    public PurchaseOrder findById(Integer id) {
        return purchaseOrderDao.getOne(id);
    }

    public PurchaseOrder persist(PurchaseOrder purchaseOrder) {
        return purchaseOrderDao.save(purchaseOrder);
    }

    public boolean delete(Integer id) {
        purchaseOrderDao.deleteById(id);
        return false;
    }

    public List<PurchaseOrder> search(PurchaseOrder purchaseOrder) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<PurchaseOrder> purchaseRequestExample = Example.of(purchaseOrder, matcher);
        return purchaseOrderDao.findAll(purchaseRequestExample);
    }

    public List<PurchaseOrder> findByGoodReceivedNoteState(PurchaseOrderStatus purchaseOrderStatus) {
        return purchaseOrderDao.findByPurchaseOrderStatus(purchaseOrderStatus);
    }

    public PurchaseOrder lastPurchaseOrder() {
    return purchaseOrderDao.findFirstByOrderByIdDesc();
    }
}