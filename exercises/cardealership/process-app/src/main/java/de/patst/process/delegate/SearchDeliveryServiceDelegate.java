package de.patst.process.delegate;

import de.patst.process.model.DeliveryServiceDTO;
import java.util.Objects;
import java.util.Random;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SearchDeliveryServiceDelegate implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchDeliveryServiceDelegate.class);

  @Value("${app.service.search-delivery-services.url}")
  private String deliveryServiceUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public void execute(DelegateExecution execution) {
    LOGGER.info("Searching delivery service for processInstanceId={} with businessKey={}",
        execution.getProcessInstanceId(), execution.getBusinessKey());

    DeliveryServiceDTO[] deliveryServices = restTemplate.getForObject(
        deliveryServiceUrl,
        DeliveryServiceDTO[].class);
    Objects.requireNonNull(deliveryServices);

    // here should be some logic to pick a delivery service
    // Otherwise a user task can be implemented if choosing is not possible automatically
    // we just use a random delivery service. Its possible we ask a already rejected delivery service again.
    DeliveryServiceDTO deliveryService = deliveryServices[new Random().nextInt(deliveryServices.length)];
    execution.setVariable("deliveryService", deliveryService);

    LOGGER.info("Picked {} as delivery service.", deliveryService.getName());
  }
}
