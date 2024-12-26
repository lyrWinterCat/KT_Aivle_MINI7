package com.aivle.mini7.service;

import com.aivle.mini7.client.dto.StartHospitalDto;
import com.aivle.mini7.dto.Log2Dto;
import com.aivle.mini7.model.Hospital;
import com.aivle.mini7.model.Log2;
import com.aivle.mini7.repository.HospitalRepository;
import com.aivle.mini7.repository.Log2Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class Log2Service {
    private final HospitalRepository hospitalRepository;
    private final Log2Repository log2Repository;

    //조회
    @Transactional(readOnly = true)
    public Page<Log2Dto.ResponseList> getLogList(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id") // id 기준 내림차순 정렬
        );
        Page<Log2> logs = log2Repository.findAll(sortedPageable);
        return logs.map(Log2Dto.ResponseList::of);
    }

    //삽입
    public Log2 insertLog(Log2 log2) {
        return log2Repository.save(log2);
    }

    //추천
//    @Transactional(readOnly = true)
//    public Page<HospitalDto.ResponseList> getHospitalList(Pageable pageable){
//        Page<Hospital> hospitals = hospitalRepository.findAll(pageable);
//        return hospitals.map(HospitalDto.ResponseList::of);
//    }

    @Transactional(readOnly = true)
    public List<Hospital> getHospitalList(Integer pk) {
        return hospitalRepository.findByLog2Id(pk); // 수정된 메서드명 사용
    }

    //삽입
    public Hospital insertHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    //출발 수정
    public void updateStartHospital(StartHospitalDto startHospital) {
        Log2 log = log2Repository.findById(startHospital.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID: " + startHospital.getClientId()));
        log.setHospitalName(startHospital.getHospitalName());
        log.setAddress(startHospital.getAddress());
        log.setDuration(startHospital.getDuration());

        //날짜 포맷팅
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        log.setStart_time(formatter.format(new Date()));

        log2Repository.save(log);

    }

    //도착 수정
    public void updateEndHospital(Integer clientId) {
        Log2 log = log2Repository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID: " + clientId));
        // 날짜 포맷팅
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        log.setEnd_time(formatter.format(new Date()));

    // duration
    // 시간 형식 지정
        DateTimeFormatter formatter_duration = DateTimeFormatter.ofPattern("HH:mm:ss");

    // 문자열을 LocalTime 객체로 변환
        LocalTime time1 = LocalTime.parse(log.getStart_time(), formatter_duration);
        LocalTime time2 = LocalTime.parse(log.getEnd_time(), formatter_duration);

    // 두 시간의 차이를 계산
        Duration duration = Duration.between(time1, time2);
        long minutesDifference = Math.abs(duration.toMinutes()); // 절대값으로 계산

        log.setReal_duration((int) minutesDifference);


        log2Repository.save(log);
    }


}
