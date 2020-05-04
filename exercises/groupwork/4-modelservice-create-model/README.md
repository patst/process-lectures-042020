# 4 - Dynamically create BPMN models from Eventlog

Often processes already exist but are not explicitly modelled in a company.

Log files written by applications can be used to create an event log which then can be used to create a process model.

The α-algorithm can be used to create a process model out of an Event log.
See chapter 6 *Process intelligence* in the business engineering script how the α-algorithm works.

Tasks:
* use the event log provided as `CSV` and `XES` file (both describe the same process, use whatever you like) to analyze the process
* use the Camunda BPMN Model API to create a process model
* deploy the process model in the process engine
* what steps can be improved manually in the detected model?

Hints:
* would be great if the event log analysis and model creation is completely automated. If that does not work try to apply the α-algorithm manually and create the model from the result.
* if you find a library for applying the α-algorithm to an event log you can use it
* your Java code should follow the default coding guidelines from https://www.sonarlint.org/

## Resources

* BPMN Model API: https://docs.camunda.org/manual/7.12/user-guide/model-api/bpmn-model-api/
* Visualize generated BPMN files: https://bpmn.io/

