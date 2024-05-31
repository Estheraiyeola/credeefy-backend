package com.semicolon.credeefy.data.repository;

import com.semicolon.credeefy.data.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Institution findInstitutionByEmail(String email);
}
