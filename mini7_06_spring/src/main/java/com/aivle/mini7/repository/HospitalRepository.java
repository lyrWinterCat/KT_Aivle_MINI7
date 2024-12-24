package com.aivle.mini7.repository;

import com.aivle.mini7.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, String> {
}
