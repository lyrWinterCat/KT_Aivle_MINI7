package com.aivle.mini7.repository;

import com.aivle.mini7.model.Log2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Log2Repository extends JpaRepository<Log2,String> {
}
