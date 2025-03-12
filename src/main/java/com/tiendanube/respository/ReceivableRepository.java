package com.tiendanube.respository;

import com.tiendanube.model.Receivable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivableRepository extends JpaRepository<Receivable, String> {
}
