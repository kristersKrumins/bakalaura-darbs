# Retail Discount Calculator

This is a Spring Boot application that calculates the net price on a retail bill considering various discount rules. It includes unit tests and code coverage reports using SonarQube.

## Prerequisites

- Java 17
- Maven 3.6.3 or later
- SonarQube (installed and running locally or on a remote server)
- SonarScanner (installed and configured)

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/besafx/retail.git
cd retail
```

### Build the Project
To build the project, run the following Maven command:
```bash
mvn clean install
```

### Run the Application
To run the Spring Boot application, use the following Maven command:
```bash
mvn spring-boot:run
```
The application will start on http://localhost:8080

### Run Tests
To execute the unit tests, run:
```bash
mvn test
```

### UML
![UML](https://raw.githubusercontent.com/besafx/retail/master/UML.png)


### Run static code analysis
You can run the Checkstyle analysis with the following Maven command:
```bash
mvn checkstyle:check
```

### Generate Test Coverage Report
To generate a test coverage report, you can use the JaCoCo Maven plugin. The report will be generated in the target/site/jacoco directory.
```bash
mvn clean test jacoco:report
```
Open the index.html file in the target/site/jacoco directory to view the coverage report.

## SonarQube
After installing SonarQube and SonarScanner and adding sonar plugin to pom.

### Run SonarQube Analysis
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=Retail-Discount-Calculator \
  -Dsonar.projectName='Retail Discount Calculator' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=squ_2e1dcd886a6f6ce68133f7d11e0b12fa39dea366
```

### SonarQube Result
![SonarCubeResult](https://raw.githubusercontent.com/besafx/retail/master/SonarCubeResult.png)

### Bonus points
I created a new feature branch, 'feature/use_discount_policies,' to add enhancements and improvements for code quality, as well as to handle another discount rule for customers who have bills older than 2 years. I then merged it into the master branch.

Here is final report for SonarQube after merging
![Final_1](https://raw.githubusercontent.com/besafx/retail/master/Final_1.png)
![Final_2](https://raw.githubusercontent.com/besafx/retail/master/Final_2.png)
