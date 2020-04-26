package de.patst.serviceprovider.controller;

import de.patst.serviceprovider.DeliveryServices;
import de.patst.serviceprovider.model.DeliveryServiceDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AssignDeliveryOrderController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignDeliveryOrderController.class);

  private final TaskExecutor taskExecutor;

  private RestTemplate restTemplate;

  @Autowired
  public AssignDeliveryOrderController(TaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
    this.restTemplate = new RestTemplate();
  }

  @PutMapping(value = "/delivery-service/{deliveryServiceId}/order", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> assignDelivery(
      @PathVariable("deliveryServiceId") Long deliveryServiceId,
      @RequestParam String processInstanceId,
      @RequestParam String respondTo,
      @RequestBody Map<String, Object> deliveryRequest) {
    LOGGER.info("Got delivery request for processinstance {} and respondto url {}", processInstanceId, respondTo);
    this.taskExecutor.execute(() -> this.assignDeliveryResponse(deliveryServiceId, processInstanceId, respondTo));
    return ResponseEntity.accepted().build();
  }

  protected RestTemplate getRestTemplate() {
    return restTemplate;
  }

  private synchronized void assignDeliveryResponse(Long deliveryServiceId, String processInstanceId, String respondTo) {
    try {
      //Wait a second to simulate work
      this.wait(1000);
    } catch (InterruptedException e) {
    }
    Optional<DeliveryServiceDTO> deliveryService = DeliveryServices.DELIVERY_SERVICES
        .stream()
        .filter(ds -> ds.getId().equals(deliveryServiceId))
        .findFirst();
    if (deliveryService.isPresent()) {
      Map<String, Object> response = new HashMap<>();
      response.put("status", new Random().nextBoolean() ? "accepted" : "rejected");
      response.put("processInstanceId", processInstanceId);
      response.put("deliveryPrice", new Random().nextInt(2000));

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<Map<String, Object>> entity = new HttpEntity<>(response, headers);
      restTemplate.exchange(respondTo, HttpMethod.PUT, entity, Void.class);
    } else {
      LOGGER.error("Delivery service for ID {} not found", deliveryServiceId);
    }
  }
}
