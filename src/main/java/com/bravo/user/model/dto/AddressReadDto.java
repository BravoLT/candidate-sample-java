package com.bravo.user.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressReadDto {

    private String id;
    private String address;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private int zip;
    private LocalDateTime updated;

}