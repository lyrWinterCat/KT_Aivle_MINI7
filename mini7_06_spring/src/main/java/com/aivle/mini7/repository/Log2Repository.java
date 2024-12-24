package com.aivle.mini7.repository;

import com.aivle.mini7.model.Log2;
import org.hibernate.query.Page;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Log2Repository extends JpaRepository<Log2,Integer> {
}
