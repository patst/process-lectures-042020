# Java delegates

The task is to implement a Java Delegate which adds up numbers.

The test class `de.patst.process.testing.AdditionProcessTest` contains the test cases which need to pass.

## Implementation

Create a class `de.patst.process.delegate.AdditionDelegate` under `src/main/java` which implements the `JavaDelegate` interface and
implements the required method.

The `AdditionDelegate` class must have a `@org.springframework.stereotype.Component` annotation in order to be picked up by the
spring container. More information about the inversion of control pattern and dependency injection can be found here: https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring 

The process is started with two process variables named `summand1` and `summand2` from type `Double` 
and is expected to write the sum of these two variables into a new variable called `sum` (also typed as `Double`).

After the implementation is done the `de.patst.process.testing.AdditionProcessTest.testSum()` test case is supposed to be successful.
In order to execute it remove the @Ignore annotation at the testcase.
