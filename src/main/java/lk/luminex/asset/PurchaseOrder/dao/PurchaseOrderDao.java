package lk.luminex.asset.PurchaseOrder.dao;



import lk.luminex.asset.PurchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.luminex.asset.PurchaseOrder.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PurchaseOrderDao extends JpaRepository<PurchaseOrder, Integer> {
    List<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus);

    PurchaseOrder findFirstByOrderByIdDesc();
}
