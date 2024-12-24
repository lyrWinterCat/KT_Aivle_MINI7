package com.aivle.mini7.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="hospital")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @Column(nullable=false)
    private String address;

    @Column(nullable=false)
    private String tel1;

    @Column(nullable=false)
    private String tel2;

    @Column(nullable=false)
    private String type;

    @Column(nullable=false)
    private Integer distance;

    @Column(nullable=false)
    private String duration;

    @OneToOne(mappedBy = "index")
    @JsonBackReference
    private Log2 id;
}
