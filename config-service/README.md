
## Features
- **Backend**: Spring Boot application for configurations management.

### `Configuration.java`
- **Entity**: `Configuration`
- **Table**: `CONFIGURATION`
- **Fields**:
    - `id` (Long, Primary Key, Auto-generated)
    - `name` (String, Not Null, Unique)
    - `display` (String)
    - `prefix` (String)
    - `suffix` (String)
    - `length` (Integer)
    - `configOrder` (Integer, Not Null, Unique)
    - `initValue` (Integer)

### How to Run `config-service`

1. **Build the Project**:
   ```sh
   mvn clean package
   ```

2. **Run the Application**:
   ```sh
   java -jar target/config-service-0.0.1-SNAPSHOT.jar
   ```

3. **Access the Service**:
   Open your browser and navigate to `http://localhost:8081`.
## Configuration Docker

### `config-service/Dockerfile`
- **Base Image**: OpenJDK 17 (slim)
- **Working Directory**: `/app`
- **JAR File**: `target/config-service-0.0.1-SNAPSHOT.jar`
- **Exposed Port**: 8081
- **Entry Point**: `java -jar config-service.jar`