package de.patst.process.delegate;

import de.patst.process.model.CarContractDTO;
import de.patst.process.model.DeliveryRequestDTO;
import de.patst.process.model.DeliveryServiceDTO;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AssignDeliveryOrderToServiceDelegate implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignDeliveryOrderToServiceDelegate.class);

  @Value("${app.service.assign-delivery.url}")
  private String assignDeliveryServiceUrl;

  @Value("${app.self}")
  private String appContext;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public void execute(DelegateExecution execution) {
    LOGGER.info("Assigning delivery order to delivery service for processInstanceId={} with businessKey={}",
        execution.getProcessInstanceId(), execution.getBusinessKey());

    DeliveryServiceDTO deliveryService = (DeliveryServiceDTO) execution.getVariable("deliveryService");
    CarContractDTO carContract = (CarContractDTO) execution.getVariable("carContract");

    DeliveryRequestDTO deliveryRequest = new DeliveryRequestDTO(
        carContract.getCustomerAddress(),
        carContract.getCarModel(),
        execution.getProcessInstanceId()

    );

    Map<String, String> uriVars = new HashMap<>();
    uriVars.put("deliveryServiceId", deliveryService.getId().toString());
    uriVars.put("processInstanceId", execution.getProcessInstanceId());
    uriVars.put("respondTo", appContext + "/delivery-response");
    restTemplate.put(assignDeliveryServiceUrl,
        deliveryRequest,
        uriVars);
  }
}
