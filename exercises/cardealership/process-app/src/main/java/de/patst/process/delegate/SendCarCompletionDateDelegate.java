package de.patst.process.delegate;

import de.patst.process.model.CarCompletionDateDTO;
import de.patst.process.model.CarContractDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SendCarCompletionDateDelegate implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendCarCompletionDateDelegate.class);

  @Override
  public void execute(DelegateExecution execution) {
    LOGGER.info("Sending car completion date for processInstanceId={} with businessKey={}",
        execution.getProcessInstanceId(), execution.getBusinessKey());

    CarContractDTO productionOrder = (CarContractDTO)
        execution.getVariable("productionOrder");
    java.util.Date date = (java.util.Date)
        Objects.requireNonNull(
            execution.getVariable("carCompletionDate"));

    CarCompletionDateDTO carCompletionDate = new CarCompletionDateDTO(
        LocalDateTime.from(date.toInstant().atZone(ZoneId.systemDefault())),
        productionOrder.getId());

    execution.getProcessEngineServices().getRuntimeService()
        .createMessageCorrelation("CarCompletionDateMessage")
        .setVariable("carCompletionDate", carCompletionDate)
        .correlate();

  }
}
