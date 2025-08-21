# jBPM to Kogito Migration Demo

This project demonstrates the migration from jBPM to Kogito for business process management in a Spring Boot application. It showcases a simple leave request approval process that was originally designed for jBPM but has been adapted to work with both traditional service approaches and Kogito.

## Table of Contents
- [Development Setup & Requirements](#development-setup--requirements)
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Business Process](#business-process)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Migration Notes](#migration-notes)
- [Testing the Application](#testing-the-application)

## Development Setup & Requirements

### 🛠️ Required Tools & Applications

#### **Core Development Tools**
| Tool | Version | Purpose | Download Link |
|------|---------|---------|---------------|
| **Java JDK** | 17+ (LTS) or 21+ | Programming language runtime | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) / [OpenJDK](https://openjdk.org/) |
| **Maven** | 3.6.0+ | Build tool and dependency management | [Apache Maven](https://maven.apache.org/download.cgi) |
| **Git** | Latest | Version control | [Git SCM](https://git-scm.com/downloads) |

#### **IDE Recommendations**
| IDE | Version | Pros | Download Link |
|-----|---------|------|---------------|
| **Visual Studio Code** | Latest | Lightweight, excellent extensions | [VS Code](https://code.visualstudio.com/) |
| **IntelliJ IDEA** | 2023.1+ | Full-featured Java IDE | [JetBrains](https://www.jetbrains.com/idea/) |
| **Eclipse IDE** | 2023-03+ | Free, robust Java development | [Eclipse](https://www.eclipse.org/downloads/) |

### 📦 Technology Stack & Versions

#### **Framework Dependencies**
```xml
<!-- Core Spring Boot -->
<spring-boot.version>3.1.6</spring-boot.version>
<java.version>17</java.version>

<!-- jBPM/Kogito -->
<kogito.version>1.44.1.Final</kogito.version>
<jbpm.version>7.74.1.Final</jbpm.version>

<!-- Testing -->
<junit.version>5.9.3</junit.version>
<mockito.version>5.3.1</mockito.version>
```

#### **Key Dependencies**
- **Spring Boot Starter Web**: REST API and web layer
- **Spring Boot Starter Actuator**: Health checks and monitoring
- **Spring Boot Starter Test**: JUnit 5, Mockito, Spring Test
- **Spring Boot DevTools**: Hot reload and development utilities
- **Jackson**: JSON serialization/deserialization
- **Kogito BOM**: Business process management framework

### 🧩 VS Code Extensions (Recommended)

#### **Essential Extensions**
| Extension | Publisher | Purpose | Install Command |
|-----------|-----------|---------|----------------|
| **Extension Pack for Java** | Microsoft | Complete Java development | `ext install vscjava.vscode-java-pack` |
| **Spring Boot Extension Pack** | VMware | Spring Boot development tools | `ext install vmware.vscode-boot-dev-pack` |
| **Maven for Java** | Microsoft | Maven project management | `ext install vscjava.vscode-maven` |
| **Debugger for Java** | Microsoft | Java debugging capabilities | `ext install vscjava.vscode-java-debug` |

#### **BPMN & Process Modeling**
| Extension | Publisher | Purpose | Install Command |
|-----------|-----------|---------|----------------|
| **BPMN Editor** | Red Hat | Visual BPMN process design | `ext install redhat.vscode-bpmn-editor` |
| **XML Tools** | Josh Johnson | XML formatting and validation | `ext install dotjoshjohnson.xml` |

#### **Code Quality & Testing**
| Extension | Publisher | Purpose | Install Command |
|-----------|-----------|---------|----------------|
| **SonarLint** | SonarSource | Code quality analysis | `ext install sonarsource.sonarlint-vscode` |
| **Test Runner for Java** | Microsoft | JUnit test execution | `ext install vscjava.vscode-java-test` |
| **Coverage Gutters** | ryanluker | Test coverage visualization | `ext install ryanluker.vscode-coverage-gutters` |

#### **Development Productivity**
| Extension | Publisher | Purpose | Install Command |
|-----------|-----------|---------|----------------|
| **GitLens** | GitKraken | Enhanced Git capabilities | `ext install eamodio.gitlens` |
| **REST Client** | Huachao Mao | API testing directly in VS Code | `ext install humao.rest-client` |
| **Auto Rename Tag** | Jun Han | Paired tag renaming | `ext install formulahendry.auto-rename-tag` |
| **Bracket Pair Colorizer** | CoenraadS | Visual bracket matching | `ext install coenraads.bracket-pair-colorizer` |

### ⚙️ VS Code Configuration

#### **Workspace Settings (`.vscode/settings.json`)**
```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml",
    "java.saveActions.organizeImports": true,
    "spring-boot.ls.logfile": {
        "on": true
    },
    "files.exclude": {
        "**/target": true,
        "**/.classpath": true,
        "**/.project": true,
        "**/.settings": true,
        "**/.factorypath": true
    }
}
```

#### **Launch Configuration (`.vscode/launch.json`)**
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Run jBPM Demo Application",
            "request": "launch",
            "mainClass": "com.jrrd.jbpmdemo.JbpmdemoApplication",
            "projectName": "jbpmdemo",
            "args": "",
            "envFile": "${workspaceFolder}/.env"
        },
        {
            "type": "java",
            "name": "Debug jBPM Demo Application",
            "request": "launch",
            "mainClass": "com.jrrd.jbpmdemo.JbpmdemoApplication",
            "projectName": "jbpmdemo",
            "args": "",
            "vmArgs": "-Dspring.profiles.active=debug"
        }
    ]
}
```

### 🔧 Environment Setup

#### **Java Environment Variables**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%

# Linux/macOS
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

#### **Maven Environment Variables**
```bash
# Windows
set MAVEN_HOME=C:\Program Files\Apache\maven
set PATH=%MAVEN_HOME%\bin;%PATH%

# Linux/macOS
export MAVEN_HOME=/opt/maven
export PATH=$MAVEN_HOME/bin:$PATH
```

### 🚀 Quick Setup Script

#### **Windows Setup (PowerShell)**
```powershell
# Check prerequisites
java -version
mvn -version
git --version

# Clone and setup project
git clone https://github.com/kiddogreed/spring-jbpm.git
cd spring-jbpm
mvn clean compile
mvn test
mvn spring-boot:run
```

#### **Linux/macOS Setup (Bash)**
```bash
#!/bin/bash
# Check prerequisites
java -version
mvn -version
git --version

# Clone and setup project
git clone https://github.com/kiddogreed/spring-jbpm.git
cd spring-jbpm
mvn clean compile
mvn test
mvn spring-boot:run
```

### 🐳 Docker Development (Optional)

#### **Dockerfile for Development**
```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/jbpmdemo-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

#### **Docker Compose for Full Stack**
```yaml
version: '3.8'
services:
  jbpm-app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
    depends_on:
      - postgres
  
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: jbpmdb
      POSTGRES_USER: jbpm
      POSTGRES_PASSWORD: jbpm123
    ports:
      - "5432:5432"
```

### 📋 Development Checklist

Before starting development, ensure:

- [ ] **Java 17+** installed and configured
- [ ] **Maven 3.6+** installed and accessible via PATH
- [ ] **Git** configured with user credentials
- [ ] **VS Code** with Java Extension Pack installed
- [ ] **Spring Boot Extension Pack** installed
- [ ] **BPMN Editor** extension for process modeling
- [ ] Project cloned and dependencies resolved (`mvn clean compile`)
- [ ] Tests passing (`mvn test`)
- [ ] Application starts successfully (`mvn spring-boot:run`)
- [ ] REST endpoints accessible (`curl http://localhost:8080/actuator/health`)

### 🔍 Troubleshooting

#### **Common Issues**
| Issue | Solution |
|-------|----------|
| **Java version mismatch** | Ensure JAVA_HOME points to JDK 17+ |
| **Maven not found** | Add Maven bin directory to PATH |
| **Tests failing** | Check Java 23 compatibility issues with Mockito |
| **Port 8080 in use** | Change server.port in application.properties |
| **BPMN editor not working** | Install Red Hat BPMN Editor extension |

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

### BPMN Process Flow Diagram

```
                                Leave Request Process Flow
                                
    [Start] → [Submit Leave Request] → [Approval Gateway] → [Auto Approve] → [End]
                                              ↓
                                      [Manager Approval] → [End]
                                      
┌─────────┐   ┌─────────────────────┐   ┌─────────────────┐   
│  Start  │──▶│ Submit Leave Request│──▶│ Approval Gateway│   
│  Event  │   │      (User Task)    │   │  (Days <= 5?)   │   
└─────────┘   └─────────────────────┘   └─────────────────┘   
                                                │
                                        ┌───────┴───────┐
                                        ▼               ▼
                               ┌─────────────────┐ ┌──────────────────┐ 
                               │  Auto Approve   │ │ Manager Approval │ 
                               │ (Service Task)  │ │   (User Task)    │ 
                               └─────────────────┘ └──────────────────┘ 
                                        │               │
                                        ▼               ▼
                               ┌─────────────────┐ ┌──────────────────┐ 
                               │   Auto End      │ │  Manager End     │ 
                               │  (End Event)    │ │  (End Event)     │ 
                               └─────────────────┘ └──────────────────┘ 
```

### BPMN Process Definition

The `leave.bpmn` file defines the business process with:

#### Process Elements
- **Start Event** (`startEvent`): Process initiation
- **User Task** (`submitLeaveRequestTask`): Submit Leave Request - collects employee name and days requested
- **Exclusive Gateway** (`approvalGateway`): Decision point based on days requested
- **Service Task** (`autoApproveTask`): Automatic approval for requests ≤ 5 days
- **User Task** (`managerApprovalTask`): Manual approval for requests > 5 days  
- **End Events**: Process completion paths for both auto and manual approval

#### Business Rules
- **Auto-Approval Condition**: `return daysRequested <= 5;`
- **Manager Approval Condition**: `return daysRequested > 5;`

#### Process Variables
- `employeeName` (String): Name of the employee requesting leave
- `daysRequested` (Integer): Number of days requested
- `approved` (Boolean): Approval status of the request

### BPMN File Content

<details>
<summary>Click to view the complete BPMN 2.0 XML definition</summary>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                   xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" 
                   xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" 
                   xmlns:bpsim="http://www.bpsim.org/schemas/1.0" 
                   xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" 
                   xmlns:di="http://www.omg.org/spec/DD/20100524/DI" 
                   xmlns:drools="http://www.jboss.org/drools" 
                   id="_l9gQ4WBfED6FcdlRiwXjrg" 
                   exporter="jBPM Process Modeler" 
                   exporterVersion="2.0" 
                   targetNamespace="http://www.omg.org/bpmn20">
  
  <!-- Process Variable Definitions -->
  <bpmn2:itemDefinition id="_employeeNameItem" structureRef="String"/>
  <bpmn2:itemDefinition id="_daysRequestedItem" structureRef="Integer"/>
  <bpmn2:itemDefinition id="_approvedItem" structureRef="Boolean"/>
  
  <!-- Main Process Definition -->
  <bpmn2:process id="leave" 
                 drools:packageName="com.jrrd.jbpmdemo" 
                 drools:version="1.0" 
                 drools:adHoc="false" 
                 name="leave" 
                 isExecutable="true" 
                 processType="Public">
    
    <!-- Process Variables -->
    <bpmn2:property id="employeeName" itemSubjectRef="_employeeNameItem" name="employeeName"/>
    <bpmn2:property id="daysRequested" itemSubjectRef="_daysRequestedItem" name="daysRequested"/>
    <bpmn2:property id="approved" itemSubjectRef="_approvedItem" name="approved"/>
    
    <!-- Sequence Flows -->
    <bpmn2:sequenceFlow id="Flow_1leb6fz" sourceRef="startEvent" targetRef="submitLeaveRequestTask"/>
    <bpmn2:sequenceFlow id="Flow_1hns7cv" sourceRef="submitLeaveRequestTask" targetRef="approvalGateway"/>
    
    <!-- Auto-Approval Flow (Days <= 5) -->
    <bpmn2:sequenceFlow id="Flow_03h4cfe" name="Days &lt;= 5" sourceRef="approvalGateway" targetRef="autoApproveTask">
      <bpmn2:conditionExpression xsi:type="bpmn2:tFormalExpression" language="http://www.java.com/java">
        <![CDATA[return daysRequested <= 5;]]>
      </bpmn2:conditionExpression>
    </bpmn2:sequenceFlow>
    
    <!-- Manager Approval Flow (Days > 5) -->
    <bpmn2:sequenceFlow id="Flow_19m76sz" name="Days > 5" sourceRef="approvalGateway" targetRef="managerApprovalTask">
      <bpmn2:conditionExpression xsi:type="bpmn2:tFormalExpression" language="http://www.java.com/java">
        <![CDATA[return daysRequested > 5;]]>
      </bpmn2:conditionExpression>
    </bpmn2:sequenceFlow>
    
    <bpmn2:sequenceFlow id="Flow_1qm6rhh" sourceRef="autoApproveTask" targetRef="autoApproveEnd"/>
    <bpmn2:sequenceFlow id="Flow_1kl2djq" sourceRef="managerApprovalTask" targetRef="managerApproveEnd"/>
    
    <!-- Process Elements -->
    <bpmn2:startEvent id="startEvent">
      <bpmn2:outgoing>Flow_1leb6fz</bpmn2:outgoing>
    </bpmn2:startEvent>
    
    <bpmn2:userTask id="submitLeaveRequestTask" name="Submit Leave Request">
      <bpmn2:incoming>Flow_1leb6fz</bpmn2:incoming>
      <bpmn2:outgoing>Flow_1hns7cv</bpmn2:outgoing>
      <!-- Task input/output specifications for employee name and days requested -->
    </bpmn2:userTask>
    
    <bpmn2:exclusiveGateway id="approvalGateway" name="Approval Gateway" gatewayDirection="Diverging">
      <bpmn2:incoming>Flow_1hns7cv</bpmn2:incoming>
      <bpmn2:outgoing>Flow_03h4cfe</bpmn2:outgoing>
      <bpmn2:outgoing>Flow_19m76sz</bpmn2:outgoing>
    </bpmn2:exclusiveGateway>
    
    <bpmn2:serviceTask id="autoApproveTask" name="Auto Approve" implementation="Java">
      <bpmn2:incoming>Flow_03h4cfe</bpmn2:incoming>
      <bpmn2:outgoing>Flow_1qm6rhh</bpmn2:outgoing>
      <!-- Automatic approval service implementation -->
    </bpmn2:serviceTask>
    
    <bpmn2:userTask id="managerApprovalTask" name="Manager Approval">
      <bpmn2:incoming>Flow_19m76sz</bpmn2:incoming>
      <bpmn2:outgoing>Flow_1kl2djq</bpmn2:outgoing>
      <!-- Manager approval task for requests > 5 days -->
    </bpmn2:userTask>
    
    <bpmn2:endEvent id="autoApproveEnd">
      <bpmn2:incoming>Flow_1qm6rhh</bpmn2:incoming>
    </bpmn2:endEvent>
    
    <bpmn2:endEvent id="managerApproveEnd">
      <bpmn2:incoming>Flow_1kl2djq</bpmn2:incoming>
    </bpmn2:endEvent>
  </bpmn2:process>
  
  <!-- Service Interface Definition -->
  <bpmn2:interface id="_F01E9913-0211-4F51-89BB-EF68CD1BCC0A_ServiceInterface" 
                   name="com.jrrd.jbpmdemo.service.LeaveApprovalService" 
                   implementationRef="com.jrrd.jbpmdemo.service.LeaveApprovalService">
    <bpmn2:operation id="_F01E9913-0211-4F51-89BB-EF68CD1BCC0A_ServiceOperation" 
                     name="autoApprove" 
                     implementationRef="autoApprove"/>
  </bpmn2:interface>
  
  <!-- BPMN Diagram Information (Visual Layout) -->
  <bpmndi:BPMNDiagram>
    <bpmndi:BPMNPlane bpmnElement="leave">
      <!-- Visual positioning and layout information for BPMN elements -->
      <!-- This section defines the visual representation of the process -->
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn2:definitions>
```

</details>

### Process Flow Explanation

1. **Process Start**: The process begins with a start event
2. **Leave Request Submission**: Employee fills out leave request form (User Task)
3. **Decision Gateway**: System evaluates the number of days requested
   - **Path A** (≤ 5 days): Automatic approval via Service Task
   - **Path B** (> 5 days): Requires manager approval via User Task
4. **Process End**: Both paths lead to their respective end events

This BPMN definition serves as the blueprint for the business process, even though the current implementation uses a simplified service approach due to Kogito dependency complexities.

## Technologies Used

### 🏗️ **Core Framework Stack**
| Technology | Version | Purpose | Documentation |
|------------|---------|---------|---------------|
| **Java** | 17+ (LTS) | Programming language runtime | [Oracle Java Docs](https://docs.oracle.com/en/java/javase/17/) |
| **Spring Boot** | 3.1.6 | Application framework and auto-configuration | [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/3.1.6/reference/htmlsingle/) |
| **Maven** | 3.6.0+ | Build automation and dependency management | [Maven User Guide](https://maven.apache.org/guides/) |

### 🔄 **Business Process Management**
| Technology | Version | Purpose | Documentation |
|------------|---------|---------|---------------|
| **jBPM** | 7.74.1.Final | Traditional BPM engine (legacy support) | [jBPM Documentation](https://docs.jboss.org/jbpm/release/7.74.1.Final/jbpm-docs/html_single/) |
| **Kogito** | 1.44.1.Final | Cloud-native business automation platform | [Kogito Documentation](https://docs.kogito.kie.org/latest/html_single/) |
| **BPMN 2.0** | Standard | Business Process Model and Notation | [BPMN Specification](https://www.omg.org/spec/BPMN/2.0/) |

### 🌐 **Web & API Technologies**
| Technology | Version | Purpose | Documentation |
|------------|---------|---------|---------------|
| **Spring Web MVC** | 6.0.13 | REST API framework | [Spring Web MVC](https://docs.spring.io/spring-framework/docs/6.0.13/reference/html/web.html) |
| **Jackson** | 2.15.2 | JSON serialization/deserialization | [Jackson Documentation](https://github.com/FasterXML/jackson-docs) |
| **Spring Boot Actuator** | 3.1.6 | Production monitoring and management | [Actuator Guide](https://docs.spring.io/spring-boot/docs/3.1.6/reference/html/actuator.html) |

### 🧪 **Testing Framework**
| Technology | Version | Purpose | Documentation |
|------------|---------|---------|---------------|
| **JUnit 5** | 5.9.3 | Unit testing framework | [JUnit 5 User Guide](https://junit.org/junit5/docs/5.9.3/user-guide/) |
| **Mockito** | 5.3.1 | Mocking framework for unit tests | [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) |
| **Spring Boot Test** | 3.1.6 | Integration testing support | [Testing in Spring Boot](https://docs.spring.io/spring-boot/docs/3.1.6/reference/html/features.html#features.testing) |
| **AssertJ** | 3.24.2 | Fluent assertion library | [AssertJ Documentation](https://assertj.github.io/doc/) |

### 🛠️ **Development Tools**
| Technology | Version | Purpose | Documentation |
|------------|---------|---------|---------------|
| **Spring Boot DevTools** | 3.1.6 | Hot reload and development utilities | [DevTools Features](https://docs.spring.io/spring-boot/docs/3.1.6/reference/html/using.html#using.devtools) |
| **Maven Surefire Plugin** | 3.0.0 | Test execution and reporting | [Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/) |
| **Maven Compiler Plugin** | 3.11.0 | Java compilation configuration | [Compiler Plugin](https://maven.apache.org/plugins/maven-compiler-plugin/) |

### 📊 **Complete Dependency Tree**

#### **Production Dependencies**
```xml
<!-- Core Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.1.6</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
    <version>3.1.6</version>
</dependency>

<!-- jBPM/Kogito BOM -->
<dependency>
    <groupId>org.kie.kogito</groupId>
    <artifactId>kogito-bom</artifactId>
    <version>1.44.1.Final</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

<!-- Development Tools -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <version>3.1.6</version>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

#### **Test Dependencies**
```xml
<!-- Spring Boot Test Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <version>3.1.6</version>
    <scope>test</scope>
</dependency>

<!-- JUnit 5 Jupiter API -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
</dependency>

<!-- Mockito for Java unit testing -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.3.1</version>
    <scope>test</scope>
</dependency>
```

### 🏷️ **Version Compatibility Matrix**
| Java Version | Spring Boot | Maven | JUnit | Mockito | Kogito |
|--------------|-------------|-------|-------|---------|--------|
| **17 (LTS)** | 3.1.6 ✅ | 3.6+ ✅ | 5.9.3 ✅ | 5.3.1 ✅ | 1.44.1 ✅ |
| **21 (LTS)** | 3.1.6 ✅ | 3.6+ ✅ | 5.9.3 ✅ | 5.3.1 ✅ | 1.44.1 ✅ |
| **23** | 3.1.6 ⚠️ | 3.6+ ✅ | 5.9.3 ✅ | 5.3.1 ⚠️ | 1.44.1 ✅ |

> **Note**: Java 23 has compatibility issues with Mockito due to Byte Buddy limitations. Use Java 17 or 21 for full testing functionality.

### 🎯 **Architecture Patterns Used**
- **MVC (Model-View-Controller)**: Separation of concerns in web layer
- **Service Layer Pattern**: Business logic encapsulation
- **DTO (Data Transfer Object)**: API data contracts
- **Repository Pattern**: Data access abstraction (in-memory implementation)
- **Dependency Injection**: IoC container management via Spring
- **REST API Design**: Resource-based API endpoints

## Getting Started

> 📋 **For detailed setup instructions, see the [Development Setup & Requirements](#development-setup--requirements) section above.**

### Quick Start

#### **Prerequisites Verification**
Before proceeding, ensure you have the required tools installed:

```bash
# Verify Java installation (should be 17+)
java -version

# Verify Maven installation (should be 3.6+)
mvn -version

# Verify Git installation
git --version
```

#### **Project Setup**

1. **Clone the repository**
   ```bash
   git clone https://github.com/kiddogreed/spring-jbpm.git
   cd spring-jbpm
   ```

2. **Build and verify the project**
   ```bash
   # Clean and compile
   mvn clean compile
   
   # Run tests to verify setup
   mvn test
   
   # Package the application
   mvn package
   ```

3. **Run the application**
   ```bash
   # Start with Maven
   mvn spring-boot:run
   
   # OR run the JAR directly
   java -jar target/jbpmdemo-0.0.1-SNAPSHOT.jar
   ```

4. **Verify the application is running**
   ```bash
   # Check application health
   curl http://localhost:8080/actuator/health
   
   # Test leave request creation
   curl -X POST -H "Content-Type: application/json" \
     -d '{"employeeName": "John Doe", "daysRequested": 3}' \
     http://localhost:8080/api/leave
   ```
   
   Expected responses:
   - Health check: `{"status":"UP"}`
   - Leave request: JSON response with `id`, `approved: true`, and `approvalDate`

#### **Development Environment Setup**

5. **Configure VS Code (recommended)**
   ```bash
   # Install essential extensions
   code --install-extension vscjava.vscode-java-pack
   code --install-extension vmware.vscode-boot-dev-pack
   code --install-extension redhat.vscode-bpmn-editor
   
   # Open project in VS Code
   code .
   ```

6. **Alternative IDEs**
   - **IntelliJ IDEA**: Import as Maven project, ensure Spring Boot plugin is enabled
   - **Eclipse**: Import existing Maven project, install Spring Tools 4 plugin

#### **API Testing Tools**

For testing REST endpoints, you can use:

| Tool | Purpose | Command/URL |
|------|---------|-------------|
| **cURL** | Command-line HTTP client | `curl -X POST -H "Content-Type: application/json" -d '{"employeeName":"John","daysRequested":3}' http://localhost:8080/api/leave` |
| **Postman** | GUI-based API testing | [Download Postman](https://www.postman.com/downloads/) |
| **VS Code REST Client** | API testing within VS Code | Create `.http` files with requests |
| **HTTPie** | User-friendly command-line HTTP client | `http POST localhost:8080/api/leave employeeName=John daysRequested:=3` |

#### **VS Code REST Client Example**
Create a file named `api-test.http` in your project root:

```http
### Health Check
GET http://localhost:8080/actuator/health

### Create Leave Request (Auto-approved)
POST http://localhost:8080/api/leave
Content-Type: application/json

{
  "employeeName": "Alice Smith",
  "daysRequested": 3
}

### Create Leave Request (Manual approval required)
POST http://localhost:8080/api/leave
Content-Type: application/json

{
  "employeeName": "Bob Johnson",
  "daysRequested": 10
}

### Get All Leave Requests
GET http://localhost:8080/api/leave

### Get Specific Leave Request (replace {id} with actual ID)
GET http://localhost:8080/api/leave/{id}

### Approve Leave Request (replace {id} with actual ID)
PUT http://localhost:8080/api/leave/{id}/approve

### Reject Leave Request (replace {id} with actual ID)
PUT http://localhost:8080/api/leave/{id}/reject
```

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

### Automated Testing Suite

The application includes comprehensive JUnit 5 tests covering all layers:

#### Test Structure
```
src/test/java/
├── com/jrrd/jbpmdemo/
│   ├── JbpmdemoApplicationTests.java          # Integration tests
│   ├── model/
│   │   └── LeaveRequestTest.java              # Model/Entity tests
│   ├── service/
│   │   └── SimpleLeaveRequestServiceTest.java # Service layer tests
│   ├── dto/
│   │   └── LeaveRequestDTOTest.java           # Data Transfer Object tests
│   └── controller/
│       └── LeaveRequestControllerTest.java    # REST API tests
```

#### Running Tests

**Run all tests:**
```bash
mvn test
```

**Run specific test classes:**
```bash
# Model tests
mvn test -Dtest=LeaveRequestTest

# Service tests  
mvn test -Dtest=SimpleLeaveRequestServiceTest

# DTO tests
mvn test -Dtest=LeaveRequestDTOTest

# Controller tests (currently disabled due to MockMvc configuration)
mvn test -Dtest=LeaveRequestControllerTest

# Application context tests
mvn test -Dtest=JbpmdemoApplicationTests
```

#### Test Coverage

**Model Layer (LeaveRequestTest):**
- ✅ Constructor validation with auto-approval logic
- ✅ Getter/setter functionality  
- ✅ Business logic validation (5-day threshold)
- ✅ Edge cases (boundary conditions, null handling)
- ✅ Data integrity checks

**Service Layer (SimpleLeaveRequestServiceTest):**
- ✅ CRUD operations (Create, Read, Update, Delete)
- ✅ Auto-approval workflow testing
- ✅ Manual approval/rejection workflow
- ✅ Null parameter handling
- ✅ Concurrent access patterns
- ✅ Business rule validation

**DTO Layer (LeaveRequestDTOTest):**
- ✅ JSON serialization/deserialization
- ✅ Field validation
- ✅ Edge case handling
- ✅ Special character support

**Integration Layer (JbpmdemoApplicationTests):**
- ✅ Spring Boot application context loading
- ✅ Bean configuration validation

#### Test Results Summary

```
[INFO] Tests run: 51, Failures: 0, Errors: 0, Skipped: 0
```

**Test Breakdown:**
- **Model Tests**: 18 tests covering domain logic
- **Service Tests**: 20 tests covering business operations  
- **DTO Tests**: 12 tests covering data transfer
- **Integration Tests**: 1 test covering application startup

#### Key Test Scenarios

**Auto-Approval Logic:**
```java
@Test
void shouldAutoApproveForFiveDaysOrLess() {
    LeaveRequest request = new LeaveRequest("John Doe", 5);
    assertTrue(request.getApproved());
    assertNotNull(request.getApprovalDate());
}

@Test  
void shouldNotAutoApproveForMoreThanFiveDays() {
    LeaveRequest request = new LeaveRequest("Jane Smith", 10);
    assertFalse(request.getApproved());
    assertNull(request.getApprovalDate());
}
```

**Service Workflow Testing:**
```java
@Test
void shouldApproveExistingPendingRequest() {
    String requestId = service.createLeaveRequest("Employee", 10);
    assertFalse(service.getLeaveRequest(requestId).getApproved());
    
    boolean result = service.approveLeaveRequest(requestId);
    assertTrue(result);
    assertTrue(service.getLeaveRequest(requestId).getApproved());
}
```

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

### Test Configuration

The project uses:
- **JUnit 5**: Modern testing framework
- **Mockito**: For mocking dependencies  
- **Spring Boot Test**: For integration testing
- **MockMvc**: For REST API testing (when properly configured)
- **Jackson**: For JSON serialization testing

### Continuous Integration

Tests are designed to run in CI/CD pipelines with:
- Fast execution (< 10 seconds total)
- No external dependencies
- Comprehensive coverage of business logic
- Clear test reporting

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
