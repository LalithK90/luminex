package lk.luminex.asset.supplier.dao;

import lk.luminex.asset.supplier.entity.Supplier;
import lk.luminex.asset.supplier_item.entity.enums.ItemSupplierStatus;
import lk.luminex.asset.viva.entity.SupplierItemObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {
    Supplier findFirstByOrderByIdDesc();

    Supplier findByIdAndItemSupplierStatus(Integer supplierId, ItemSupplierStatus itemSupplierStatus);

    Supplier findByEmail(String email);

    Supplier findByName(String name);


}