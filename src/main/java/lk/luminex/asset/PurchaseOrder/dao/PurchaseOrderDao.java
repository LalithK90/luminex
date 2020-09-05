package lk.luminex.asset.PurchaseOrder.dao;

import lk.luminex.asset.PurchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.luminex.asset.PurchaseOrder.entity.PurchaseOrder;
import lk.luminex.asset.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderDao extends JpaRepository< PurchaseOrder, Integer> {

    List<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus);

    List<PurchaseOrder> findByPurchaseOrderStatusAndSupplier(PurchaseOrderStatus purchaseOrderStatus, Supplier supplier);


    PurchaseOrder findFirstByOrderByIdDesc();
}
