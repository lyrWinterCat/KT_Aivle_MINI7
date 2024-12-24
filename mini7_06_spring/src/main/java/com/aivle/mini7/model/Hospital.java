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

    @ManyToOne
    @JoinColumn(name="id")//@JoinColumn(name="외래키이름")
    @JsonBackReference
    private Log2 log2; //기사-댓글로서 댓글이 자식임
}
