# 3 - Generate Constants for BPMN Elements

During the lectures we observed that we have to use string literals during testing and process implementation.
This is very error prone because the naming for messages or signals might change.

At best, we detect these changes during test execution but maybe detection happens even later during integration testing or even in production.

It would be great to have a Java class with constants for **messages** and **signals** to reference them safely during unit testing and delegate implementation.

Tasks:
* use the Model API to read a BPMN Model and find all **signals** and **messages**
* generate a Java class with constants for these signal and message names
* is there a different option than the Model API to get the signal and message names?
* are there other names/ elements in a BPMN model which might be interesting to be referenced by name? 
* think about ways how to integrate the constant generation into the (maven) build process

Hints:
* write JUnit tests
* your Java code should follow the default coding guidelines from https://www.sonarlint.org/

## Resources

* `model-api` example project: https://github.com/patst/process-examples/tree/master/model-api
* Read a BPMN Model: https://docs.camunda.org/manual/7.12/user-guide/model-api/bpmn-model-api/read-a-model/ 
