package de.patst.process.controller;

import de.patst.process.MessageConstants;
import de.patst.process.model.CarCompletionDateDTO;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarCompletionDateController {

  @Autowired
  private RuntimeService runtimeService;

  @PostMapping("/completion-date")
  public void correlateCarCompletionDate(
      @RequestBody CarCompletionDateDTO carCompletionDate) {
    // correlate a message
    runtimeService.createMessageCorrelation(MessageConstants.CAR_COMPLETION_DATE_RECEIVED)
        .setVariable("completionDate", carCompletionDate)
        .processInstanceBusinessKey(carCompletionDate.getContractId()) // to get a unique process instance!
        .correlateExclusively();
  }

}
