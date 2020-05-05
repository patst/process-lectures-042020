package de.patst.process.controller;


import de.patst.process.model.DeliveryResponseDTO;
import java.util.Objects;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryResponseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryResponseController.class);

  @Autowired
  private RuntimeService runtimeService;

  @PutMapping("/delivery-response")
  public void deliveryResponse(@RequestBody DeliveryResponseDTO response) {
    LOGGER.info("Got response from delivery service: {}", response);
    // handle response (correlation)
    MessageCorrelationResult correlationResult = runtimeService
        .createMessageCorrelation("DeliveryServiceResponseReceivedMessage")
        .processInstanceId(response.getProcessInstanceId())
        .setVariable("deliveryResponse", response)
        .correlateWithResult();
    Objects.requireNonNull(correlationResult);

    LOGGER.info("Correlated message for process instance {}", correlationResult.getExecution().getProcessInstanceId());
  }
}

