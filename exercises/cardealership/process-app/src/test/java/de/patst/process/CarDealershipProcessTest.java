package de.patst.process;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;

import de.patst.process.controller.DeliveryResponseController;
import de.patst.process.model.AddressDTO;
import de.patst.process.model.CarCompletionDateDTO;
import de.patst.process.model.CarContractDTO;
import de.patst.process.model.CarInvoiceDTO;
import de.patst.process.model.DeliveryResponseDTO;
import de.patst.process.model.DeliveryServiceDTO;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarDealershipProcessTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(CarDealershipProcessTest.class);

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private DeliveryResponseController deliveryResponseController;

  @MockBean
  //  @Autowired
  private RestTemplate restTemplate;

  /**
   * Simulates one successful execution of the process instance. All messages with are received from external actors are sent manually.
   */
  @Test
  public void executeProcessInstanceSuccessful() throws InterruptedException {
    String businessKey = "55555";
    CarContractDTO carContract = new CarContractDTO();
    carContract.setCarModel("VW-Polo");
    carContract.setCustomerEmail("max.meier@gmx.net");
    carContract.setId(businessKey);
    carContract.setCustomerAddress(new AddressDTO("Freundallee", "15", "30173", "Hannover"));

    // Return an array of delivery services as mocked response
    Mockito.when(restTemplate.getForObject(anyString(), any()))
        .thenReturn(
            new DeliveryServiceDTO[]{
                new DeliveryServiceDTO(1L, "Test")
            }
        );

    // The mock method must be defined before the service is called.
    // But at this point the processInstanceId is no set.
    // Because the response is send async we can store the id in this variable at a later point
    // To reference it the variable needs to be effective final
    String[] processInstanceId = new String[1];
    Mockito.doAnswer(invocation -> {
      Executors.newFixedThreadPool(1).submit(() -> {
        try {
          // Simulate some times that passes until the answer arrives
          Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        DeliveryResponseDTO response = new DeliveryResponseDTO(processInstanceId[0], "accepted", 123d);
        deliveryResponseController.deliveryResponse(response);
      });
      return null;
    }).when(restTemplate).put(anyString(), anyObject(), anyMap());

    ProcessInstance pi = this.runtimeService
        .createMessageCorrelation(MessageConstants.CAR_CONTRACT_CREATED_MESSAGE)
        .processInstanceBusinessKey(businessKey)
        .setVariable("carContract", carContract)
        .correlateStartMessage();
    processInstanceId[0] = pi.getId();

    // Another option for message correlation:
    // Map<String,Object > variables = new HashMap<>();
    // variables.put("completionDateResponse", new CarCompletionDateDTO(LocalDateTime.now().plusMonths(1), carContract.getId()));
    //runtimeService.correlateMessage(MessageConstants.CAR_COMPLETION_DATE_RECEIVED,businessKey, variables);

    // Test if the delivery service variable has the correct value
    assertThat(pi)
        .hasVariables("deliveryService")
        .variables().hasEntrySatisfying("deliveryService", deliveryService -> Assert.assertEquals("Test", ((DeliveryServiceDTO) deliveryService).getName()));

    // We need to send the message events manually.
    runtimeService.createMessageCorrelation(MessageConstants.CAR_COMPLETION_DATE_RECEIVED)
        .setVariable("completionDateResponse",
            new CarCompletionDateDTO(LocalDateTime.now().plusMonths(1), carContract.getId()))
        .processInstanceBusinessKey(businessKey)
        .correlate();

    // wait until the response from the async service was returned
    // wait a maximum of 10 seconds
    for (int i = 0; i < 10; i++) {
      if (runtimeService.createProcessInstanceQuery().activityIdIn("CarInvoiceReceivedEvent").list().isEmpty()) {
        LOGGER.info("Process is not waiting for the CarInvoiceReceivedMessage. Sleeping for one second");
        Thread.sleep(1000);
      } else {
        break;
      }
    }

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
