package com.bravo.user.dao.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bravo.user.model.dto.AddressReadDto;

import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {

  @Id
  @Column(name = "id")
  private String id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "address_line1", nullable = false)
  private String addressLine1;

  @Column(name = "address_line2")
  private String addressLine2;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "state", nullable = false)
  private String state;
  
  @Column(name = "zip", nullable = false)
  private String zip;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Address(){
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }

  public Address(final AddressReadDto address){
    this();
    this.user = new User();
    this.user.setId(address.getUserId());
    this.addressLine1 = address.getAddressLine1();
    this.addressLine2 = address.getAddressLine2();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
  }
}
