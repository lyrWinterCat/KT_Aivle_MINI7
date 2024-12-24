package com.aivle.mini7.service;

import com.aivle.mini7.dto.Log2Dto;
import com.aivle.mini7.model.Log2;
import com.aivle.mini7.repository.Log2Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class Log2Service {
    private final Log2Repository log2Repository;
    //조회
    @Transactional(readOnly = true)
    public Page<Log2Dto.ResponseList> getLogList(Pageable pageable){
        Page<Log2> logs = log2Repository.findAll(pageable);

        return logs.map(Log2Dto.ResponseList::of);
    }
    //삽입
    public Log2 insertLog(Log2 log2) {
        return log2Repository.save(log2);
    }

}
