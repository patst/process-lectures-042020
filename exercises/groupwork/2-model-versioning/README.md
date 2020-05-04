# 2 - Process Versioning

BPMN Models change over time. 
Whenever a `.bpmn` file is changed a new process model version is created at application startup.

This can be more challenging if process instances are long-running or the BPMN model changes frequently.

Tasks:
* describe what kind of changes might happen to a process and how to handle them (not only the model can change!)
    - describe which changes are more problematic than others
* model an example application and demonstrate how versioning works
* show how process instances can be migrated to a new model version

Hints:
* write JUnit test for your examples
* your Java code should follow the default coding guidelines from https://www.sonarlint.org/

## Resources:

Process Versioning: https://docs.camunda.org/manual/7.12/user-guide/process-engine/process-versioning/ 
Process Instance Migration: https://docs.camunda.org/manual/7.12/user-guide/process-engine/process-instance-migration/
