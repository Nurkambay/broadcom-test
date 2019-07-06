# Project description

broadcom-test application is java spring boot project with maven build script
This application represents vending machine emulation.

## 1. Project structure
Project contain 3 modules
- broadcom-core
- broadcom-storage
- broadcom-rest
 

### 1.1. broadcom-core
Main core classes with model, repository and business logic layers.

- Core is not depended on data persistence
- Core is not depended on data visualization and population
- Business logic is not thread-safe because responsibility on this lays on core's consumers 

### 1.2. broadcom-storage
Module with repository in-memory storage implementation.
- In current implementation contain only in-memory storage for easy deploy purposes.
- You can easily autowire repository beans with Hibernate JPA, DynamoDB, no-sql, TCP/IP or other implementation.

### 1.3. broadcom-rest
This module contains REST application. It depends on core and storage modules.

#### How to switch to Database implementation 
- Switch dependency from broadcom-storage to another storage implementation.
- Make ApiService class transactional by adding @Transactional annotation. Because the responsibility of core's usage lays on this class as on the only consumer.

## 2. How to build and run
- Install maven
- type "maven clean install"
- type "java -jar ./broadcom-rest/target/broadcom.jar"
- open url http://localhost:5000
