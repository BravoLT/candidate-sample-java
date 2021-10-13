package com.bravo.user.dao.model;

import com.bravo.user.model.dto.UserSaveDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;


@Entity
@Data
@Table(name = "address")

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {


    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "line1", nullable = false)
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip", nullable = false)
    private int zip;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;






}
