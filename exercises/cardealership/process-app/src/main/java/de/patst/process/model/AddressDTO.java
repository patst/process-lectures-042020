package de.patst.process.model;

import java.io.Serializable;

public class AddressDTO implements Serializable {

  private String street;
  private String houseNumber;
  private String zipCode;
  private String city;

  public AddressDTO() {
  }

  public AddressDTO(String street, String houseNumber, String zipCode, String city) {
    this.street = street;
    this.houseNumber = houseNumber;
    this.zipCode = zipCode;
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
