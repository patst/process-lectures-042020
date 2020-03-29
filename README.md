# Process automation resources

This repository contains resources for the business engineering process automation part.

## Repository layout

The folder `examples` contains the projects and source code shown during the lecture. 
The examples are a git submodule for the repository https://github.com/patst/process-examples.git

The folder `exercises` contains the project templates for the exercises.

The projects can be imported into an IDE independent. 

## Runtime environment

For implementing and building the exercises this is needed:

* Development environment, e.g.
    * [Eclipse for Java Developers](https://www.eclipse.org/downloads/packages/release/2020-03/r/eclipse-ide-java-developers)
    * [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/#section=mac)
* [Java 11 JDK](https://adoptopenjdk.net/installation.html?variant=openjdk11&jvmVariant=hotspot) 

Maven is used as dependency management framework. Maven can be bootstrapped and executed using the maven wrapper:

```
./mwnw install
```

Apart from the command line Eclipse has an own Maven installation which is working as well.

The project can be imported in any Java IDE with Maven support and should be working.

## Running the application

All examples are Spring-Boot applications.

They can be run using `./mvnw spring-boot:run`.

The cockpit is then accessible at http://localhost:8080 (Username: admin/ Password: admin)

## Additional resources and links

The following list of links may come handy for learning how to implement processes with spring-boot and Camunda bpm:

| Description   | Link          |
|:---           |:---           |
| Camunda modeler is used for creating executable BPMN models. The modeler can be used for modelling only as well | https://camunda.com/download/modeler/ |
| BPMN Reference chart with an overview of all common BPMN symbols | http://www.bpmb.de/images/BPMN2_0_Poster_EN.pdf |
| Short description of the common BPMN symbols | https://camunda.com/bpmn/reference/ | 
| The official documentation for Camunda BPM. Useful for looking up implementation details.                  | https://docs.camunda.org/manual/7.12/  |
| Collection of 'official' Camunda BPM Examples | https://github.com/camunda/camunda-bpm-examples |
| Implementation of Java Delegates | https://docs.camunda.org/manual/latest/user-guide/process-engine/delegation-code/ |