package com.bravo.user.dao.model;


import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "line1")
    @NotBlank(message = "line1 is mandatory")
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "city")
    @NotBlank(message = "city is mandatory")
    private String city;

    @Column(name = "state")
    @NotBlank(message = "state is mandatory")
    private String state;

    @Column(name = "zip")
    @NotBlank(message = "zip is mandatory")
    private int zip;

    @Column(name = "updated", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updated;

}
