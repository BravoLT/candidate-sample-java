package com.bravo.user.dao.model;

import com.bravo.user.model.dto.UserSaveDto;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Address> addressSet = new ArrayList<>();

    public User() {
        super();
        this.id = UUID.randomUUID().toString();
        this.updated = LocalDateTime.now();
    }

    public User(final UserSaveDto user) {
        this();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
    }
}
