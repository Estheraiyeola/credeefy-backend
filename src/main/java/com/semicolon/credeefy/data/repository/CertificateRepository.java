package com.semicolon.credeefy.data.repository;

import com.semicolon.credeefy.data.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
