# Exercise 1

This first exercise creates the car dealership process pool (3rd pool from the top).

![Exercise 1 .bpmn model](exercise1_model.png "Exercise 1 BPMN model")

1. Create a .bpmn diagram for the car dealership pool
2. Use ‘Send tasks’ for sending messages, ‘Service Tasks’ for every other activity
3. Create the java delegates in the package `de.patst.process.delegate`
    * in the beginning the Java delegates can be empty or just write a log message, just to get the process running

4. Create a class diagram for the business objects in the car dealership process. Potential business objects are objects send/ received in all events, e.g.:
    * Start message
    * Message events
    * Signal events
    * Send tasks
    
5. Create the java classes described in the class diagram in the package `de.patst.process.model`.

6. Write a JUnit test which tests a successful execution of the process. At the moment the other pools do not exist. 
Therefore we have to send signals and set variables manually in the unit test.
    a. Send the signal events from the test code (Use the RuntimeService) 
    b. Add variables where needed to the process instance (RuntimeService)

7. There is a form to create a new car contract in the browser at http://localhost:8080/index.html .
The form does not yet call the process engine to create the process instance. 
The call needs to be implemented in the Javascript file at `src/main/resources/static/create_contract.js`.
There are two links. One is the CamundaBPM API description and the other link shows an example how to call a REST API with Javascript.

# Exercise 2 - Car factory with user task

We create a new process for the *car factory* part of the process.

The car factory process starts with an `StartCarProductionMessage` send from the car dealership pool.
After that there is a user task where a human is calculating the date for the car completion. 
Next the car is produced and the car completion date is communicated back to the car dealership pool.

1. model the process either in an own `.bpmn` file or as another participant in the existing model
2. write unit tests for the new process. Think about an *error case* as well, e.g. the start car production message may send with wrong data (e.g. a missing car model)

# Exercise 3 - Search delivery services service call

The project `remote-services` (in the same folder like this process-app) is a Spring-Boot application and provides a **Search delivery** service.

The service usage is described in the `remote-services` README file.

1. Update the `SearchDeliveryService` delegate to get a list of delivery services with a service call.
    * start the `remote-services project to use the API
    * use a RestTemplate to execute the GET-Request (usage info: https://www.baeldung.com/rest-template#get )
    * choose a delivery service (randomly) and store it in a process variable for later usage
2. check if your unit tests are still working, fix them if necessary

# Exercise 4 - Assign delivery order to delivery service

The project `remote-services` is a Spring-Boot application and provides an asynchronous **Assign delivery order** service.

The service usage is described in the `remote-services` README file.

In exercise 3 the `SearchDeliveryService` was used to choose a delivery service.
Now you have to assign that delivery service the order to deliver the car and get the final price.

The service is asynchronous and does not return a response immediately (like the `SearchDeliveryService` did).
Instead, you have to specify a callback URL which is called after some time with the delivery services response.

The result then must be correlated with a process instances `DeliveryServiceResponseReceived`message.

Implementation steps:

1. Update the `AssignDeliveryOrderToService` delegate to call the `AssignDeliveryOrder` rest service
    * use a RestTemplate (usage info: https://www.baeldung.com/rest-template#1-simple-put-with-exchange )
    * check the usage hints in the `remote-services` README file
    * use the current process instance id as query parameter (https://launchschool.com/books/http/read/what_is_a_url#querystringsparameters )
    * provide a callback url in the `respondTo` query parameter
2. Implement a RestController which provides a PUT method for the `AssignDeliveryOrder` service response. (RestController implementation help: https://spring.io/guides/tutorials/rest/ )
    * Check the `remote-services` README file what the service response does look like
    * Implement a java class which does match the service response data structure (that means the class has an attribute for each field in the JSON response and getter/setter for this attribute)
    * when the method is called you have to correlate the `DeliveryServiceResponseReceived` message to the correct process instance and store the delivery service response as process variable
3. use the delivery services response to check if the delivery order was accepted or rejected
4. make sure the test cases work
