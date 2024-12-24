package com.aivle.mini7.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String startAddress;

    @Column(nullable = false)
    private Integer emergencyGrade;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private String real_duration;
    //foreign
    @OneToOne
    @JsonManagedReference
    private Hospital index;
}
