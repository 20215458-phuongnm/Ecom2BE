package com.mygame.repository.authentication;

import com.mygame.entity.authentication.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long>, JpaSpecificationExecutor<Verification> {

    Optional<Verification> findByUserId(Long userId);

}
