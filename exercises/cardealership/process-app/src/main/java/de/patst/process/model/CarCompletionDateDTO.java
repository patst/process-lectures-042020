package de.patst.process.model;

import java.io.Serializable;
import java.time.LocalDateTime;


public class CarCompletionDateDTO implements Serializable {

  private LocalDateTime completionDate;
  private String contractId;

  public CarCompletionDateDTO() {
  }

  public CarCompletionDateDTO(LocalDateTime completionDate, String contractId) {
    this.completionDate = completionDate;
    this.contractId = contractId;
  }

  public LocalDateTime getCompletionDate() {
    return completionDate;
  }

  public void setCompletionDate(LocalDateTime completionDate) {
    this.completionDate = completionDate;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }
}
