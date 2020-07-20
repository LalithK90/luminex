package lk.luminex.asset.supplier.dao;


import lk.luminex.asset.item.entity.Item;
import lk.luminex.asset.supplier.entity.Enum.ItemSupplierStatus;
import lk.luminex.asset.supplier.entity.Supplier;
import lk.luminex.asset.supplier.entity.SupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SupplierItemDao extends JpaRepository<SupplierItem, Integer> {
    SupplierItem findBySupplierAndItem(Supplier supplier, Item item);

    List<SupplierItem> findBySupplier(Supplier supplier);

    List<SupplierItem> findBySupplierAndItemSupplierStatus(Supplier supplier, ItemSupplierStatus itemSupplierStatus);
}
