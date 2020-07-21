# Getting familiar with K8s and Redis

Simple application based on WebFlux, which works with Redis using Reactive Driver.

Technologies used
* Java 14
* Spring Boot
* WebFlux
* Redis
* Gradle
* Groovy for tests (Spock)
* K8s
* Docker

Docker image is built using Spring Boot Gradle Plugin
`./gradlew bootBuildImage`

To launch an application locally you can use ```minikube```
