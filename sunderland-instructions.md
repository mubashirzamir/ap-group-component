# Deployment Guide

## Prerequisites

### Docker and Docker Compose Installation
- Install Docker Desktop by following the [Docker Desktop Installation Guide](https://docs.docker.com/desktop/).

### Port Availability
- Ensure the following ports are available:
  - **8081**
  - **8082**
  - **8083**
  - **8443**

## Deployment Steps

### Step 1: Unzip Files
- Unzip the provided ZIP files into a folder of your choice.
- The main folder will contain the following:
  - `docker-compose.yml`
  - `nginx.conf`
  - Directories for each microservice containing their respective `Dockerfile`.

### Step 2: Build and Run Containers
- Navigate to the folder containing `docker-compose.yml`.
- Use the following command to build and run the containers for all instances:

  ```bash
  docker-compose up --build
  ```

### Step 3: Verify Running Containers
- To check if the containers are running, execute the following command:

  ```bash
  docker ps
  ```
