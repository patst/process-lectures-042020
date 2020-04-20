package de.patst.process;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

import de.patst.process.model.AddressDTO;
import de.patst.process.model.CarCompletionDateDTO;
import de.patst.process.model.CarContractDTO;
import de.patst.process.model.CarInvoiceDTO;
import de.patst.process.model.DeliveryResponseDTO;
import java.time.LocalDateTime;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarDealershipProcessTest {

    @Autowired
    private RuntimeService runtimeService;

    /**
     * Simulates one successful execution of the process instance. All messages with are received from external actors are sent manually.
     */
    @Test
    public void executeProcessInstanceSuccessful() {
        String businessKey = "55555";
        CarContractDTO carContract = new CarContractDTO();
        carContract.setCarModel("VW-Polo");
        carContract.setCustomerEmail("max.meier@gmx.net");
        carContract.setId(businessKey);
        carContract.setCustomerAddress(new AddressDTO("Freundallee", "15", "30173", "Hannover"));

        ProcessInstance pi = this.runtimeService
            .createMessageCorrelation(MessageConstants.CAR_CONTRACT_CREATED_MESSAGE)
            .processInstanceBusinessKey(businessKey)
            .setVariable("carContract", carContract)
            .correlateStartMessage();

        // Another option for message correlation:
        // Map<String,Object > variables = new HashMap<>();
        // variables.put("completionDateResponse", new CarCompletionDateDTO(LocalDateTime.now().plusMonths(1), carContract.getId()));
        //runtimeService.correlateMessage(MessageConstants.CAR_COMPLETION_DATE_RECEIVED,businessKey, variables);

        // We need to send the message events manually.
        runtimeService.createMessageCorrelation(MessageConstants.CAR_COMPLETION_DATE_RECEIVED)
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
