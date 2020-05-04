# 5 - Cockpit plugin

Sometimes there is custom information which needs to be displayed in the process cockpit.

The car dealership wants to have additional information displayed for each process instance in the cockpit.

For each process instance order details should be displayed during process instance runtime:

![Process instance runtime](https://docs.camunda.org/manual/7.12/webapps/cockpit/img/plugin-points/plugin-point-process-instance-details.png)

Tasks:
* Create a cockpit plugin named `order-informtion-plugin` and create a tab which is displayed for running process instances
* Display information about the `CarContract` of this process instance
* For capacity planning show how many other process instances with the same `carModel` are running at the moment

Hints:
* you can use the REST-API for information gathering or write a custom database query
* write JUnit tests for the Java code
* your Java code should follow the default coding guidelines from https://www.sonarlint.org/

## Resources

* Extending Camunda Cockpit: https://docs.camunda.org/manual/7.12/webapps/cockpit/extend/plugins/
* `cockpit-plugin` example: https://github.com/patst/process-examples/tree/master/cockpit-plugin
* More Cockpit plugins as reference: https://github.com/camunda/camunda-cockpit-plugins
