package de.patst.process;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import de.patst.process.controller.DeliveryResponseController;
import de.patst.process.model.AddressDTO;
import de.patst.process.model.CarCompletionDateDTO;
import de.patst.process.model.CarContractDTO;
import de.patst.process.model.CarInvoiceDTO;
import de.patst.process.model.DeliveryResponseDTO;
import de.patst.process.model.DeliveryServiceDTO;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executors;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarDealershipProcessTest {

  @Autowired
  private RuntimeService runtimeService;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private DeliveryResponseController deliveryResponseController;

  /**
   * Simulates one successful execution of the process instance. All messages with are received from external actors are sent manually.
   */
  @Test
  public void executeProcessInstanceSuccessful() {
    String businessKey = UUID.randomUUID().toString();
    CarContractDTO carContract = new CarContractDTO();
    carContract.setCarModel("VW-Polo");
    carContract.setCustomerEmail("max.meier@gmx.net");
    carContract.setId(businessKey);
    carContract.setCustomerAddress(new AddressDTO("Freundallee", "15", "30173", "Hannover"));

    // Search for delivery services mock
    when(restTemplate.getForObject(anyString(), anyObject()))
        .thenReturn(
            new DeliveryServiceDTO[]{
                new DeliveryServiceDTO(1L, "test1"),
                new DeliveryServiceDTO(2L, "test2")
            }
        );

    // Assign delivery order mock
    doAnswer(invocation -> {
      // Add this point of implementation there is no processInstance object created, so read it at runtime.
      // Return the result async
      Executors.callable(() -> {
        String processInstanceId = BpmnAwareTests.processInstanceQuery().singleResult().getId();
        deliveryResponseController.deliveryResponse(new DeliveryResponseDTO(processInstanceId, "accepted", 123d));
      });
      return null;
    }).
        when(restTemplate).put(anyString(), anyObject(), anyMap());

    ProcessInstance pi = this.runtimeService
        .createMessageCorrelation("CarContractCreatedMessage")
        .processInstanceBusinessKey(businessKey)
        .setVariable("carContract", carContract)
        .correlateStartMessage();

    // We need to send the message events manually.
    runtimeService.createMessageCorrelation("CarCompletionDateReceivedMessage")
        .setVariable("completionDateResponse",
            new CarCompletionDateDTO(LocalDateTime.now().plusMonths(1), carContract.getId()))
        .processInstanceBusinessKey(businessKey)
        .correlate();

    runtimeService.createMessageCorrelation("DeliveryServiceResponseReceivedMessage")
        .setVariable("deliveryResponse",
            new DeliveryResponseDTO(businessKey, "accepted", 123.567))
        .processInstanceBusinessKey(businessKey)
        .correlate();

    runtimeService.createMessageCorrelation("CarInvoiceReceivedMessage")
        .setVariable("carInvoice",
            new CarInvoiceDTO(businessKey, 42000d))
        .processInstanceBusinessKey(businessKey)
        .correlate();

    assertThat(pi)
        .hasVariables("carContract")
        .isEnded()
        .hasPassed("SuccessfulEndEvent");
  }

}
