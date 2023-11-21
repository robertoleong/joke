# Developer Manual for NS Task

## Run the application

### 1. Using a docker image
#### Download and run image (make sure you have docker installed for your OS)

docker run -p 8080:8080 ghcr.io/robertoleong/joke:latest

### Test with curl

curl -w '\n' localhost:8080/api/joke

### Test in browser 

http://localhost:8080/api/joke

### 2. From github repository
### Download repository

git clone https://github.com/robertoleong/joke.git

### Go to the joke directory and build:

mvn clean package

### Start spring-boot application (stop docker container if it's still running)

mvn spring-boot:start

### Test with curl

curl -w '\n' localhost:8080/api/joke

### Test in browser

http://localhost:8080/api/joke

### Stop spring-boot application 

mvn spring-boot:stop

## 3. Extra functionality - JPA

Everytime a random joke is generated it's saved to an in-memory SQL database using JPA.

A Rest Api was created to search the saved jokes. To view all stored jokes use one of the following methods

http://localhost:8080/api/search

curl -w '\n' localhost:8080/api/search

A **pattern** parameter can be added to filter the search

http://localhost:8080/api/search?pattern=somevalue

curl -w '\n' localhost:8080/api/search?pattern=somevalue

## 4. Implementation of requirements

After reading the requirements for this task, several assumptions were made after careful deliberation
which will be explained in this section.

The filters are not user configurable (by adding extra parameters to the request).
It's possible to change them under ~/joke/src/main/resources/application.properties
in property

**joke.url.blacklist**

Any filter supported by the jokeapi can be added or removed here.

The **amount** property should be a value between 1-10.

Changing **type** will cause the application to behave erratically.

To reflect the changes in **application.properties** a rebuild of the application is required.

## 5. Swagger

Swagger documentation be accessed here:

http://localhost:8080/swagger-ui/index.html

## 6. Run using https

A docker image has been created with https support

docker run -p 443:8443 ghcr.io/robertoleong/joke-https:latest

A self signed certificate is used which will cause curl and browsers to complain.

### Test with curl

curl -w '\n' --insecure https://localhost/api/joke

### Test in browser

https://localhost/api/joke

## 7. Implementation details
To be discussed in a interview.

## 8. Testing
Jacoco is used to generate testing coverage, the file can be found in this location:
(assuming maven was used to package the application)

~/joke/target/site/jacoco$/index.html

From our testing in linux, firefox displays the page correctly, chrome not.

## 9. CI/CD
There's a github action that triggers on every push request. It builds, tests and creates
a docker image which is then uploaded to a [docker repository](https://github.com/robertoleong/joke/pkgs/container/joke)

## 10. EKS
The application has been deployed in a Amazon Kubernetes cluster with 3 nodes.
For budget reasons is currently unavailable. It will be available in an interview.