package com.mygame.repository.inventory;

import com.mygame.entity.inventory.DocketReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocketReasonRepository extends JpaRepository<DocketReason, Long>, JpaSpecificationExecutor<DocketReason> {}
