package de.patst.process.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SearchDeliveryServiceDelegate implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchDeliveryServiceDelegate.class);

  @Override
  public void execute(DelegateExecution execution) {
    LOGGER.info("Searching delivery service for processInstanceId={} with businessKey={}",
        execution.getProcessInstanceId(), execution.getBusinessKey());
  }
}
