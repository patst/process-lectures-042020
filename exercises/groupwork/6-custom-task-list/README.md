# 6 - Custom Tasklist

The camunda embedded tasklist has only limited features.
In a real world usecase there are often existing workflow systems for handling workflow automation.

The process engine must handle this usecase and has to be integrated in the existing application landscape.

There are interfaces to get information about active tasks which needs to be displayed in an existing workflow systems UI.

Tasks:
* create a simple web application displaying a list of active **UserTasks**
* add a possibility to Claim and Complete a task
* what other kinds of actions might be interesting for handling User Tasks? How can they be integrated?
* can we use the custom task list somehwere in the car dealership process?

Hints:
* place your `*.html` and `*.js` files in the folder `src/main/resources/static` of your process application to access them. (Like in the car dealership example)
* write JUnit tests for the Java code
* your Java code should follow the default coding guidelines from https://www.sonarlint.org/

## Resources

* Camunda REST API: https://docs.camunda.org/manual/7.12/reference/rest/
