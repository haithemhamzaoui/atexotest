
# **Dynamic ID Generation Application**

This project is a microservices-based application that allows users to configure criteria for generating unique IDs for individuals. It consists of three main components:

1. **Config-Service**: Manages configuration criteria for ID generation.
2. **Generation-Service**: Generates unique IDs based on the configured criteria.
3. **Frontend**: A Vue.js application for interacting with the backend services.

---

## **Table of Contents**

- [Features](#features)
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Configuration](#configuration)
- [Services and Setup](#services-and-setup)
    - [Config-Service](#config-service)
    - [Generation-Service](#generation-service)
    - [Frontend](#frontend)
- [Running the Application](#running-the-application)
    - [Using Docker Compose](#using-docker-compose)
- [API Endpoints](#api-endpoints)

---

## **Features**

- Dynamic configuration of ID generation rules:
    - Prefix, suffix, length, and order for each field.
    - Initial value for counters.
- Unique ID generation based on configured rules.
- User-friendly web interface for configuration and ID generation.
- Microservices architecture for scalability and maintainability.

---

## **Architecture**

```plaintext
+-----------------+    +-------------------+    +-------------------+
|  Frontend (Vue) | -> | Config-Service    | -> | Generation-Service |
+-----------------+    +-------------------+    +-------------------+
```

- **Frontend**: Interacts with Config-Service and Generation-Service via REST APIs.
- **Config-Service**: Stores and manages configuration rules.
- **Generation-Service**: Generates IDs based on the rules fetched from Config-Service.

---

## **Technologies Used**

- **Backend**:
    - Spring Boot (Java 17)
    - H2 (In-memory database)
    - REST API
- **Frontend**:
    - Vue.js
    - Axios
- **Containerization**:
    - Docker
    - Docker Compose

---

## **Configuration**

### **Default Configuration Parameters**

| Field          | Description                            | Default Values                  |
|----------------|----------------------------------------|----------------------------------|
| **Nom**        | Prefix, suffix, length, order          | `Prefix: "", Suffix: "", Order: 1` |
| **Prénom**     | Prefix, suffix, length, order          | `Prefix: "", Suffix: "", Order: 2` |
| **Date of Birth** | Year format with prefix and suffix    | `Prefix: "", Suffix: "", Order: 3` |
| **Compteur**   | Initial value, prefix, suffix, order   | `Prefix: "", Initial: 0, Order: 4` |

---

## **Services and Setup**

### **1. Config-Service**

- **Purpose**: Manages the configuration criteria for ID generation.
- **Port**: `8081`

#### **Setup**

1. Build the project:
   ```bash
   mvn clean package
   ```
2. Run using Docker:
   ```bash
   docker build -t config-service ./config-service
   docker run -p 8081:8081 config-service
   ```

---

### **2. Generation-Service**

- **Purpose**: Generates unique IDs based on the configured criteria.
- **Port**: `8082`

#### **Setup**

1. Build the project:
   ```bash
   mvn clean package
   ```
2. Run using Docker:
   ```bash
   docker build -t generation-service ./generation-service
   docker run -p 8082:8082 generation-service
   ```

---

### **3. Frontend**

- **Purpose**: Web interface for configuring rules and generating IDs.
- **Port**: `8080`

#### **Setup**

1. Install dependencies:
   ```bash
   npm install
   ```
2. Run the development server:
   ```bash
   npm run serve
   ```
3. Build and serve using Docker:
   ```bash
   docker build -t frontend ./frontend
   docker run -p 3000:8080 frontend
   ```

---

## **Running the Application**

### **Using Docker Compose**

1. Build and start all services:
   ```bash
   docker-compose up --build
   ```
2. Access the services:
    - **Frontend**: [http://localhost:3000](http://localhost:3000)
    - **Config-Service**: [http://localhost:8081](http://localhost:8081)
    - **Generation-Service**: [http://localhost:8082](http://localhost:8082)


---

## **API Endpoints**

### **Config-Service**

| Method | Endpoint                    | Description                      |
|--------|-----------------------------|----------------------------------|
| `GET`  | `/api/config`               | Fetch existing configurations   |
| `POST` | `/api/config`       | Save new configurations         |
| `DELETE` | `/api/config/reset` | Reset configurations            |

---

### **Generation-Service**

| Method | Endpoint          | Description                                |
|--------|-------------------|--------------------------------------------|
| `POST` | `/api/generate` | Generate a unique ID based on input fields |
| `GET`  | `/api/generate` | Fetch existing generations                 |

---

## **Frontend Features**

- **Configuration Page**:
    - Define prefix, suffix, length, and order for fields.
    - Reset configurations to defaults.
    - Save configurations to the backend.
- **Generate ID Page**:
    - Input personal details (name, date of birth, etc.).
    - Generate unique IDs based on saved configurations.
