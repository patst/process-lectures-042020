package de.patst.process.model;

import java.io.Serializable;

public class DeliveryResponseDTO implements Serializable {

  private String contractId;
  private String status;
  private Double deliveryPrice;

  public DeliveryResponseDTO() {
  }

  public DeliveryResponseDTO(String contractId, String status, Double deliveryPrice) {
    this.contractId = contractId;
    this.status = status;
    this.deliveryPrice = deliveryPrice;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getDeliveryPrice() {
    return deliveryPrice;
  }

  public void setDeliveryPrice(Double deliveryPrice) {
    this.deliveryPrice = deliveryPrice;
  }
}
