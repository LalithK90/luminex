package lk.luminex.asset.PurchaseOrder.dao;



import lk.luminex.asset.PurchaseOrder.entity.PurchaseOrder;
import lk.luminex.asset.PurchaseOrder.entity.PurchaseOrderItem;
import lk.luminex.asset.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PurchaseOrderItemDao extends JpaRepository<PurchaseOrderItem, Integer> {
    PurchaseOrderItem findByPurchaseOrderAndItem(PurchaseOrder purchaseOrder, Item item);
    List<PurchaseOrderItem> findByPurchaseOrder(PurchaseOrder purchaseOrder);
}
