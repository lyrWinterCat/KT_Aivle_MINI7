package com.aivle.mini7.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name ="logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Log {

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

    @Column(nullable = true)
    private String startAddress;

    @Column(nullable = false)
    private Integer emergencyGrade;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String hospitalName;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private Integer duration;

    @Column(nullable = true)
    private Integer real_duration;

    @Column(nullable = true)
    private String start_time;

    @Column(nullable = true)
    private String end_time;


    @OneToMany(mappedBy = "log")
    @JsonManagedReference
    private List<Hospital> hospitals;
}
