package de.patst.process.delegate;


import java.util.Objects;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// default bean name: className with lower first case (e.g. additionDelegate)
@Component
public class AdditionDelegate implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdditionDelegate.class);

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    LOGGER.info("Addition Delegate called");
    Object summand1 = execution.getVariable("summand1");
    Object summand2 = execution.getVariable("summand2");
    Objects.requireNonNull(summand1);
    Objects.requireNonNull(summand2);

    Number sum = Double.parseDouble(summand1.toString()) + Double.parseDouble(summand2.toString());
    LOGGER.info("sum is {}", sum);

    execution.setVariable("sum", sum);
  }
}
