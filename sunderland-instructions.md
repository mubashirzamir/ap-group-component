# Deployment Guide

## Prerequisites

### Docker and Docker Compose Installation
- Install Docker Desktop by following the [Docker Desktop Installation Guide](https://docs.docker.com/desktop/).

### Port Availability
- Ensure the following ports are available:
  - **9081**
  - **9082**
  - **9083**

## Deployment Steps

### Step 1: Unzip Files
- Clone the repo.
- Go into the Sunderland backend folder containing the following:
  - `docker-compose.yml`
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
