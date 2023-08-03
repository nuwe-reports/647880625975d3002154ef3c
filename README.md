# CHALLENGE
# Hospital AccWe Appointment Management System
## Project Description

The primary objective is to complete the implementation and development of the appointment management system of the Hospital Accwe.
The following tasks are required to be performed:

1. Create appointments using the API, adhering to specified limitations and JUnit tests. Modify `AppointmentController.java`.
2. Write JUnit tests for entities and controllers in `EntityUnitTest.java` and `EntityControllerUnitTest.java` to ensure correct implementation.
3. Focus on clean code practices. Ensure code is free from bugs and vulnerabilities.
4. Deploy the API with scalability using Kubernetes. Use Dockerfiles (`Dockerfile.mysql` and `Dockerfile.maven`) for MySQL database and microservice.

### Running the Application using Docker

#### Step 1. Build Docker images

for MySQL:

    docker build -t mysql-image -f Dockerfile.mysql .

for the application:

    docker build -t app-image -f Dockerfile.maven .

#### Step 2. Run MySQL Container

    docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD=<password> mysql-image

#### Step 3. Run Application Microservice

    docker run -d --name app-container --link mysql-container:mysql -p 8080:8080 app-image

The application should now be accessible through port 8080.






