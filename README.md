# jBPM to Kogito Migration Demo

This project demonstrates the migration from jBPM to Kogito for business process management in a Spring Boot application. It showcases a simple leave request approval process that was originally designed for jBPM but has been adapted to work with both traditional service approaches and Kogito.

## Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Business Process](#business-process)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Migration Notes](#migration-notes)
- [Testing the Application](#testing-the-application)

## Overview

This application implements a **Leave Request Management System** that demonstrates:

1. **jBPM Concepts**: Process definitions, task management, and business rules
2. **Kogito Integration**: Auto-generation of process APIs and models
3. **Spring Boot Integration**: RESTful APIs for process interaction
4. **Business Logic**: Automatic approval for requests ≤ 5 days, manual approval for longer requests

## Project Structure

```
jbpmdemo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jrrd/jbpmdemo/
│   │   │       ├── JbpmdemoApplication.java          # Main Spring Boot application
│   │   │       ├── controller/
│   │   │       │   └── LeaveRequestController.java   # REST API controller
│   │   │       ├── dto/
│   │   │       │   └── LeaveRequestDTO.java          # Data Transfer Object
│   │   │       ├── model/
│   │   │       │   └── LeaveRequest.java             # Domain model
│   │   │       └── service/
│   │   │           ├── SimpleLeaveRequestService.java # Business logic service
│   │   │           ├── LeaveApprovalService.java     # Approval service
│   │   │           └── LeaveRequestService.java.bak  # Original Kogito service (backup)
│   │   └── resources/
│   │       ├── application.properties                # Application configuration
│   │       └── leave.bpmn                            # BPMN process definition
│   └── test/
└── pom.xml                                           # Maven dependencies
```

## Business Process

### Leave Request Workflow

The application implements the following business process:

1. **Submit Leave Request**: Employee submits a leave request with their name and number of days
2. **Automatic Decision**: 
   - ≤ 5 days: **Auto-approved**
   - \> 5 days: **Requires manual approval**
3. **Process Completion**: Request is either approved or pending manager approval

### BPMN Process Definition

The `leave.bpmn` file defines the business process with:
- **Start Event**: Process initiation
- **User Task**: Submit Leave Request
- **Exclusive Gateway**: Decision point based on days requested
- **Service Tasks**: Auto-approval and manager approval paths
- **End Events**: Process completion

## Technologies Used

### Core Technologies
- **Java 17+**: Programming language
- **Spring Boot 3.1.6**: Application framework
- **Maven**: Build and dependency management

### Business Process Management
- **jBPM**: Original BPM engine (legacy approach)
- **Kogito 1.44.1.Final**: Cloud-native business process automation
- **BPMN 2.0**: Process modeling standard

### Additional Dependencies
- **Spring Boot Starter Web**: REST API support
- **Spring Boot Starter Actuator**: Application monitoring
- **Spring Boot DevTools**: Development utilities

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation and Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd jbpmdemo
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Verify the application is running**
   - Application starts on `http://localhost:8080`
   - Check logs for: "jBPM to Kogito Migration Demo Application Started!"

## API Endpoints

### Create Leave Request
Create a new leave request with automatic approval logic.

**Endpoint:** `POST /api/leave`

**Request Body:**
```json
{
  "employeeName": "John Doe",
  "daysRequested": 3
}
```

**Response:**
```
Leave request created with ID: fedccbb4-40e4-4bfb-a8e0-b3a2ef9c7533
```

**Examples:**
```bash
# Auto-approved request (≤ 5 days)
curl -X POST -H "Content-Type: application/json" \
  -d '{"employeeName": "John", "daysRequested": 3}' \
  http://localhost:8080/api/leave

# Manual approval required (> 5 days)
curl -X POST -H "Content-Type: application/json" \
  -d '{"employeeName": "Jane", "daysRequested": 7}' \
  http://localhost:8080/api/leave
```

### Get All Leave Requests
Retrieve all leave requests in the system.

**Endpoint:** `GET /api/leave`

**Response:**
```json
[
  {
    "id": "fedccbb4-40e4-4bfb-a8e0-b3a2ef9c7533",
    "employeeName": "John",
    "daysRequested": 3,
    "approved": true,
    "requestDate": "2025-08-21T12:46:20.7126336",
    "approvalDate": "2025-08-21T12:46:20.7126336"
  }
]
```

### Get Specific Leave Request
Retrieve a specific leave request by ID.

**Endpoint:** `GET /api/leave/{id}`

**Example:**
```bash
curl -X GET http://localhost:8080/api/leave/fedccbb4-40e4-4bfb-a8e0-b3a2ef9c7533
```

### Approve Leave Request
Manually approve a pending leave request.

**Endpoint:** `PUT /api/leave/{id}/approve`

**Example:**
```bash
curl -X PUT http://localhost:8080/api/leave/67b994f4-2d4c-4678-ab17-40d90bb13f4b/approve
```

### Reject Leave Request
Reject a leave request.

**Endpoint:** `PUT /api/leave/{id}/reject`

**Example:**
```bash
curl -X PUT http://localhost:8080/api/leave/67b994f4-2d4c-4678-ab17-40d90bb13f4b/reject
```

## Migration Notes

### From jBPM to Kogito

This project demonstrates the migration path from traditional jBPM to Kogito:

#### Original jBPM Approach
- Used `KieContainer` and `KieSession` for process management
- Required manual configuration of process runtime
- Heavyweight deployment with application server dependencies

#### Current Kogito Approach (Attempted)
- **Auto-generation**: Kogito generates REST APIs and model classes from BPMN
- **Cloud-native**: Designed for microservices and containerization
- **Spring Boot Integration**: Seamless integration with Spring ecosystem

#### Current Implementation (Simplified Service)
Due to dependency complexities in this demo environment, we implemented a simplified service approach that:
- Maintains the same business logic as the BPMN process
- Provides the same REST API interface
- Demonstrates the migration path and process concepts

### Key Migration Benefits
1. **Reduced Boilerplate**: Auto-generated APIs and models
2. **Cloud-Ready**: Native support for containers and Kubernetes
3. **Developer Experience**: Hot reload and development tools
4. **Performance**: Optimized for cloud-native deployment

## Testing the Application

### Manual Testing

1. **Start the application**:
   ```bash
   mvn spring-boot:run
   ```

2. **Test auto-approval** (≤ 5 days):
   ```bash
   curl -X POST -H "Content-Type: application/json" \
     -d '{"employeeName": "Alice", "daysRequested": 2}' \
     http://localhost:8080/api/leave
   ```

3. **Test manual approval** (> 5 days):
   ```bash
   curl -X POST -H "Content-Type: application/json" \
     -d '{"employeeName": "Bob", "daysRequested": 10}' \
     http://localhost:8080/api/leave
   ```

4. **View all requests**:
   ```bash
   curl -X GET http://localhost:8080/api/leave
   ```

### Expected Behavior

- **Requests ≤ 5 days**: Automatically approved (`approved: true`)
- **Requests > 5 days**: Created but not approved (`approved: false`)
- **All requests**: Assigned unique IDs and timestamps

### Monitoring

The application includes Spring Boot Actuator for monitoring:
- Health check: `http://localhost:8080/actuator/health`
- Application info: `http://localhost:8080/actuator/info`

## Business Logic Implementation

### Auto-Approval Rules
```java
// Auto-approve if days requested are <= 5
this.approved = daysRequested <= 5;
if (approved) {
    this.approvalDate = LocalDateTime.now();
}
```

### Process Flow
1. **Request Creation**: New leave request is created with unique ID
2. **Business Rule Evaluation**: Days requested compared against threshold
3. **Automatic Processing**: Approval status set based on business rules
4. **Persistence**: Request stored in in-memory storage
5. **Response**: Request ID returned to client

## Future Enhancements

1. **Full Kogito Integration**: Complete migration to Kogito with resolved dependencies
2. **Persistence**: Database integration for production use
3. **User Management**: Authentication and authorization
4. **Notifications**: Email/SMS notifications for approvals
5. **Reporting**: Analytics and reporting dashboards
6. **Workflow Management**: Advanced workflow features

## Troubleshooting

### Common Issues

1. **Port 8080 already in use**:
   ```bash
   # Change port in application.properties
   server.port=8081
   ```

2. **Java version issues**:
   - Ensure Java 17+ is installed
   - Check `JAVA_HOME` environment variable

3. **Maven build failures**:
   ```bash
   # Clean and rebuild
   mvn clean install
   ```

### Logs
Application logs provide detailed information about:
- Service initialization
- Leave request processing
- API request/response details

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is for educational purposes demonstrating jBPM to Kogito migration concepts.
