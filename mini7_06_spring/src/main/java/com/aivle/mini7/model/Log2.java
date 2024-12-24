package com.aivle.mini7.model;

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
    private String em_latitude;

    @Column(nullable = false)
    private String em_longitude;

    @Column(nullable = false)
    private Integer em_class;

    @Column(nullable = false)
    private String em_text;

    @Column(nullable = false)
    private String hospitallist;

    @Column(nullable = false)
    private String hospital;

    @Column(nullable = false)
    private String hospitalloc;

    @Column(nullable = false)
    private String exptime;

    @Column(nullable = false)
    private String realtime;
}
