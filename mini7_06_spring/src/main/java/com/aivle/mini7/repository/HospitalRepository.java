package com.aivle.mini7.repository;

import com.aivle.mini7.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
    @Query("SELECT h FROM Hospital h WHERE h.log.id = :logId")
    List<Hospital> findByLogId(@Param("logId") Integer logId);
}
