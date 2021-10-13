package com.bravo.user.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AddressSaveDto {
private String line1;
private String line2;
private String city;
private String state;
private int zip;


}
