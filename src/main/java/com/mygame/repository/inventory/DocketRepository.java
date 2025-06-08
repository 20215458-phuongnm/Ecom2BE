package com.mygame.repository.inventory;

import com.mygame.entity.inventory.Docket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocketRepository extends JpaRepository<Docket, Long>, JpaSpecificationExecutor<Docket> {}
