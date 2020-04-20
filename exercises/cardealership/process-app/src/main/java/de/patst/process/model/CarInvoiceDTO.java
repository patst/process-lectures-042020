package de.patst.process.model;

import java.io.Serializable;
import java.util.UUID;

public class CarInvoiceDTO implements Serializable {

  private final String id;
  private String contractId;
  private Double carPrice;

  public CarInvoiceDTO() {
    this.id = UUID.randomUUID().toString();
  }

  public CarInvoiceDTO(String contractId, Double carPrice) {
    this.id = UUID.randomUUID().toString();
    this.contractId = contractId;
    this.carPrice = carPrice;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public Double getCarPrice() {
    return carPrice;
  }

  public void setCarPrice(Double carPrice) {
    this.carPrice = carPrice;
  }
}
