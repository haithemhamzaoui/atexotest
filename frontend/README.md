## Features
- **Frontend**: Vue.js application for generation management.

### `GenerationComponent.vue`
- **Component**: `GenerationComponent`
- **Description**: Manages the display and interaction of generation data.
- **Props**:
    - `generation` (Object, Required)
- **Data**:
    - `generations` (Array)
- **Methods**:
    - `fetchGenerations()`: Fetches generation data from the backend.
    - `addGeneration()`: Adds a new generation.
    - `deleteGeneration(id)`: Deletes a generation by ID.

### `ConfigurationComponent.vue`
- **Component**: `ConfigurationComponent`
- **Description**: Manages the display and interaction of configuration data.
- **Props**:
    - `configuration` (Object, Required)
- **Data**:
    - `configurations` (Array)
- **Methods**:
    - `fetchConfigurations()`: Fetches configuration data from the backend.
    - `addConfiguration()`: Adds a new configuration.
    - `deleteConfiguration(id)`: Deletes a configuration by ID.

### How to Run `generation-frontend`

1. **Install Dependencies**:
   ```sh
   npm install
   ```

2. **Run the Application**:
   ```sh
   npm run serve
   ```

3. **Access the Application**:
   Open your browser and navigate to `http://localhost:8080`.

## Generation Frontend Docker

### `generation-frontend/Dockerfile`
- **Base Image**: Node 16 (alpine)
- **Working Directory**: `/app`
- **Copy Files**: Copies all files to the working directory.
- **Install Dependencies**: Runs `npm install`.
- **Build Application**: Runs `npm run build`.
- **Exposed Port**: 8080
- **Entry Point**: `npm run serve`