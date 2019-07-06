# broadcom-test project description

broadcom-test project is java spring boot REST API application with maven build script.
This application represents vending machine emulation.

## 1. Project structure
Project contain 3 modules
- broadcom-core
- broadcom-storage
- broadcom-rest
 

### 1.1. broadcom-core
Main core classes with model, repository and business logic layers.

- Core is not dependent on data persistence
- Core is not dependent on data visualization and population
- Business logic is not thread-safe or transactional because responsibility on this lays on core's consumers and can depend on repository implementation 

### 1.2. broadcom-storage
Module contains in-memory repositories implementation.

- Current implementation contains only in-memory storage for easy deploy.
- You can easily autowire repository beans with Hibernate JPA, DynamoDB, no-sql, TCP/IP or other implementation to change persistence behavior.

### 1.3. broadcom-rest
This module represents REST API application. It has dependencies on core and storage modules.

- Does not contain business logic
- Does not contain storage implementation

#### How to switch to Database implementation 
- Switch dependency from broadcom-storage to another storage implementation.
- Make ApiService class transactional by adding @Transactional annotation. Because the responsibility of core's usage lays on this class as on the only consumer.

## 2. How to build and run
- Install maven
- type "maven clean install"
- type "java -jar ./broadcom-rest/target/broadcom.jar"
- open url http://localhost:5000
