package de.patst.process.model;

import java.io.Serializable;

public class DeliveryResponseDTO implements Serializable {

  private String processInstanceId;
  private String status;
  private Double deliveryPrice;

  public DeliveryResponseDTO() {
  }

  public DeliveryResponseDTO(String processInstanceId, String status, Double deliveryPrice) {
    this.processInstanceId = processInstanceId;
    this.status = status;
    this.deliveryPrice = deliveryPrice;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
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
