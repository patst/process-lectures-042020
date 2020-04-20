package de.patst.process.model;

import java.io.Serializable;

public class DeliveryRequestDTO implements Serializable {

  private final String contractId;
  private final AddressDTO address;
  private final String carModel;

  public DeliveryRequestDTO(AddressDTO address, String carModel, String contractId) {
    this.address = address;
    this.carModel = carModel;
    this.contractId = contractId;
  }

  public AddressDTO getAddress() {
    return address;
  }

  public String getContractId() {
    return contractId;
  }

  public String getCarModel() {
    return carModel;
  }

}
