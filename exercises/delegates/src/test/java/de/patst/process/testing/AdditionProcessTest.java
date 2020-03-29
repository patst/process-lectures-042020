package de.patst.process.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AdditionProcessTest {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private HistoryService historyService;

  /**
   * Starts a process instance to add up two numbers.
   **/
  @Ignore("Remove this ignore message after implementing the process delegate")
  @Test
  public void sumTwoNumbers() {
    Map<String, Object> variables = Map.of("summand1", 3, "summand2", 9);
    ProcessInstance processInstance = this.runtimeService.startProcessInstanceByMessage("AdditionStartMessage", variables);
    processInstance.isEnded();
    HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
        .processInstanceId(processInstance.getId())
        .variableName("sum")
        .singleResult();
    assertNotNull(historicVariableInstance);
    assertEquals(12d, historicVariableInstance.getValue());
  }

}
