
# Java Inquiry Management System

## Overview
This project is a Java-based Inquiry Management System, split into two main parts: the **server** and the **client** applications. The system enables users to create, track, and manage inquiries such as requests, questions, and complaints, providing a streamlined and organized approach for handling customer interactions.

```
java-inquiry-management-system/
 ├── server/
 ├── client/
```

## Features

### Server
- Handles creation, tracking, and management of inquiries
- Supports inquiry statuses: Open, Handled, Canceled, Archived
- Assigns inquiries automatically to available agents
- Maintains statistics and reporting on inquiry volumes
- Supports agent management (add, remove, load/save)

### Client
- Command-line interface for users to interact with the system
- Create new inquiries
- View inquiry statuses and updates
- Communicate with the server via socket communication for real-time data exchange

## Inquiry Types
- **Request**: e.g. "Can I extend my subscription?"
- **Question**: e.g. "How do I operate the system?"
- **Complaint**: e.g. "The service did not work for me yesterday"

## System Requirements
- Java 11 or higher
- Maven (or another build tool as needed)
- Network access for client-server communication

## Installation & Running

### Clone the repository
```bash
git clone https://github.com/nechami795/inquiry-management-system.git
cd java-inquiry-management-system
```

### Run Server
```bash
cd server
mvn clean install
java -jar target/server.jar
```

### Run Client
```bash
cd ../client
mvn clean install
java -jar target/client.jar
```

## Usage Examples
- Open a new inquiry from the client interface
- View and monitor inquiries in various statuses
- Assign inquiries and handle them via the server
- Archive completed inquiries for documentation

## Development & Maintenance
- Version control via Git
- Follow coding standards and best practices
- Agile methodology for iterative improvements

## Contact & Support
For any questions or issues, please open an issue on the repository or contact the maintainer.

---
