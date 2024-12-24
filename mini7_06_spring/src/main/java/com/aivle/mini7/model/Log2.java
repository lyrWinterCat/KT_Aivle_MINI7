package com.aivle.mini7.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Log2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String datetime;

    @Column(nullable=false)
    private String inputText;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String startAddress;

    @Column(nullable = false)
    private Integer emergencyGrade;

    private String description;

    private String hospitalName;

    private String address;

    private Integer duration;

    private Integer real_duration;

    private String start_time;

    private String end_time;


    @OneToMany(mappedBy = "log2")
    @JsonManagedReference
    private List<Hospital> hospitals;
}
