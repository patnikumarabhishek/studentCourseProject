
# STUDENT COURSE REGISTRATION

####  Student Course registration api used to add, delete and register students and courses and vice versa

## Getting Started

Please follow the below steps to setup and run student-course-registration-api

### Prerequisites

- This project requires Java 8 need to be installed.
- Install any Java IDE (Eclipse, STS, Intellij etc..) and ensure you are able to launch
-  Clone/Checkout the project from version control system (git) and follow below steps

```
$ cd studentCourseProject
$ mvn clean install 
$ mvn spring-boot:run -e
$ Open web browser & hit the swagger url: http://localhost:8090/student-course/swagger-ui.html
```
## Development Setup

- Clone student-course-registration-api project. (git clone <repo url>)
- Open eclipse and import this project under (Existing Maven project)


## Run using docker

- cd studentCourseProject
- mvn clean install
- sudo docker build -f Docker -t student-course-project .
- sudo docker-compose -f docker-compose.yml up

## Tech stack

- Java 8
- Spring Boot: 2.1.7.RELEASE
- [Lombok For Building POJO](https://projectlombok.org/)
- H2 In-Memory DB
- Swagger2 - For API Local Testing

## Running the Unit tests
```
$ Open Terminal or commandLine window
$ cd <path/to/studentCourseProject>
$ mvn test -e
```

## Testing API
- Open http://localhost:8090/student-course/swagger-ui.html
- Add one or more course under Course Controller Api and Student with created course
- Click on Try it out and fill the input payload as per the contract.
- Finally Click on Excetue and ensure to see the 200 response with response message.
- Also you can update enrolled courses by student under student-course-register-controller