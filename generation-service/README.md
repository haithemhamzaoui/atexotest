## Features
- **Backend**: Spring Boot application for generation management.

### `Generation.java`
- **Entity**: `Generation`
- **Table**: `GENERATION`
- **Fields**:
    - `id` (Long, Primary Key, Auto-generated)
    - `name` (String, Not Null, Unique)
    - `description` (String)
    - `startDate` (Date)
    - `endDate` (Date)

### How to Run `generation-service`

1. **Build the Project**:
   ```sh
   mvn clean package
   ```

2. **Run the Application**:
   ```sh
   java -jar target/generation-service-0.0.1-SNAPSHOT.jar
   ```

3. **Access the Service**:
   Open your browser and navigate to `http://localhost:8082`.

## Generation Docker

### `generation-service/Dockerfile`
- **Base Image**: OpenJDK 17 (slim)
- **Working Directory**: `/app`
- **JAR File**: `target/generation-service-0.0.1-SNAPSHOT.jar`
- **Exposed Port**: 8082
- **Entry Point**: `java -jar generation-service.jar`