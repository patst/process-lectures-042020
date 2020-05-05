# 8 - Simulation

Simulation is used to replicate the reality with some assumptions.

The car dealership has to do some capacity planning and wants to simulate their ordering process.

Tasks:
* Create a service which starts new process instances at a distributed rate
    * give a short explanation if an exponential distribution or normal distribution fits better
* Which elements in the car dealership have the biggest influence to the execution time of the complete process?
* Simulate the completion of these tasks with a distribution (choose between normal and exponential distribution)


Hints:
* make an assumption how long the tasks need in average for completion 
* write JUnit tests for your Java code (if any)
* your Java code should follow the default coding guidelines from https://www.sonarlint.org/
* you can use the `java.util.concurrent.Executors` class to create an `Executor` to spawn new process instances parallel

## Resources

* Business engineering script chapter 7.4
* Normal distribution: https://en.wikipedia.org/wiki/Normal_distribution
* Exponential distribution: https://en.wikipedia.org/wiki/Exponential_distribution
