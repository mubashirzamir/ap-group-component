# https://frontend-ap-group-component.vercel.app/

# KF7014 Advanced Programming - Group Instructions

## Group Members

| Name                       | Email                       | City          | Ports |
|----------------------------|-----------------------------|---------------|-------|
| Alvin Kho Yien Yang        | w24017214@northumbria.ac.uk | Darlington    | 8087  |  
| Avinash Saha               | w24036703@northumbria.ac.uk | Durham        | 8082  | 
| Mubashir Zamir             | w24029487@northumbria.ac.uk | Newcastle     | 8081  | 
| Muhammad Murtaz Amir Naqvi | w24034050@northumbria.ac.uk | Sunderland    | 9084  |   
| Shubham Goswami            | w24038579@northumbria.ac.uk | Middlesbrough |       |

### Note

- Port: 8080 is reserved for the authentication server.
- Port: 5173 is reserved for the frontend.

---

## Libraries Used

| Library          | Type              | Documentation                                      |
|------------------|-------------------|----------------------------------------------------|
| **Tailwind CSS** | CSS               | [Tailwind Documentation](https://tailwindcss.com/) |
| **Ant Design**   | Component Library | [Ant Design Documentation](https://ant.design/)    |
| **Chart.js**     | Chart             | [Chart.js Documentation](https://www.chartjs.org/) |

---

## Instructions for Development

## Running the Authentication Server and Frontend

### Authentication Server

1. **Build the Authentication Server**  
   Navigate to the backend directory and build the jar file using Maven:

   ```bash
   cd /backend/master_gateway
   ./mvnw clean install
   cd /target

2. **Run the Authentication Server**  
   Run the jar file:

   ```bash
   java -jar master_gateway-0.0.1-SNAPSHOT.jar
   ```

### Frontend

1. **Install Dependencies**  
   Navigate to the frontend directory and install the dependencies:

   ```bash
   cd /frontend
   npm install
   ```

2. **Run the Frontend**

   ```bash
   npm run dev
   ```

---

### 1. Individual Assessment

Place your individual assessment files in the appropriate directory:

- `/backend/city/individual_assessment` (e.g., `/backend/newcastle/individual_assessment` for Newcastle)
- Add instructions on how to run your individual assessment in a <your-city-name>-instructions.md file in the root of
  the project, e.g., `newcastle-instructions.md`.

### 2. Frontend Development

To start developing the frontend, focus on modifying the files for your specific city located at:

- `/frontend/src/pages/Dashboard/Cities/YourCityName`

For example, you can refer to `/frontend/src/pages/Dashboard/Cities/Newcastle` for guidance on how to structure your
city’s dashboard.

### 3. Configuration

#### API Base Path

##### Development

Add your city’s API base path to the `.env.development` file in the `/frontend` directory. For example:

- e.g. `VITE_NEWCASTLE_API_BASE_URL=http://localhost:8080/city/newcastle`
- To authenticate requests through the master gateway, use the master gateway URL:
  `http://localhost:8080/city/<your-city-name>`.
    - Make sure the `application.yml` and `application-dev.yml` file in the `master_gateway` project is configured for
      your city.

##### Production

Add your city’s API base path to the `.env.production` file in the `/frontend` directory. For example:

- For this step your individual assessment should be deployed somewhere with a public URL.
    - A simple way to do this is to use ngrok to expose your localhost to the
      internet: https://ngrok.com/blog-post/free-static-domains-ngrok-users
- e.g. `VITE_NEWCASTLE_API_BASE_URL=https://gateway-ap-group-component.onrender.com/city/newcastle`
- To authenticate requests through the master gateway, use the master gateway URL:
  `https://gateway-ap-group-component.onrender.com/city/<your-city-name>`.
    - Make sure the `application.yml` and `application-prod.yml` file in the `master_gateway` project is configured for
      your city.

#### Service File

Create a service file to communicate with your API gateway for your city. The file should be located in:

- `/frontend/src/services/YourCityNameService.js`

For example, for Newcastle, the file path would be `/frontend/src/services/NewcastleService.js`.

---

## CORS Configuration

You might need to enable CORS in the API Gateways to avoid CORS errors.

You can enable CORS in your API Gateway by modifying the `application.properties` file located in:

- `/backend/newcastle/individual-assessment/individual-assessment/gateway/src/main/resources/application.properties`

---

## Git Workflow Instructions

1. **Branching Out from Main**
    - Always create a new branch from the `main` branch before making any changes.
    - Name the branch after yourself for easier identification. For example:
      ```bash
      git checkout main
      git pull origin main
      git checkout -b your-name
      ```

2. **Making Changes**
    - Work on your own branch and commit your changes regularly.
      ```bash
      git add .
      git commit -m "Your descriptive commit message"
      ```

3. **Keeping Your Branch Updated with Main**
    - Regularly pull changes from the `main` branch into your branch to keep it updated and avoid merge conflicts:
      ```bash
      git checkout main
      git pull origin main
      git checkout your-name
      git merge main
      ```

4. **Creating a Pull Request**
    - Once your changes are ready, push your branch to the remote repository:
      ```bash
      git push origin your-name
      ```
    - Open a Pull Request (PR) on the repository, targeting the `main` branch.
    - Ensure your branch is up-to-date with `main` before creating the PR.

5. **Avoid Pushing Directly to Main**
    - **Do not push your changes directly to the `main` branch**. Always create a Pull Request and wait for it to be
      reviewed and approved.

By following these steps, we can maintain a clean workflow and minimize merge conflicts.

---

## Adding Routes to `application.yml` in `master_gateway`

If you want your city-specific routes to be proxied through the main gateway, you need to add the routes to the
yml files in the `master_gateway` project.

`application.yml`

```yaml
allowed_origins: '*'
server:
  port: 8080
spring:
  profiles:
    active: dev
  application:
    name: master_gateway
  cloud:
    gateway:
      mvc:
        routes:
          - id: newcastle-service
            uri: ${gateway.uris.newcastle-service}
            predicates:
              - Path=/city/newcastle/**
            filters:
              - DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin
              - stripPrefix=2
          - id: backend-sunderland
            uri: ${gateway.uris.your-city-service}
            predicates:
              - Path=/city/sunderland/**
            filters:
              - DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin
              - stripPrefix=2
```

`application-dev.yml`

```yaml
gateway:
  uris:
    newcastle-service: http://localhost:8081
    your-city-service: http://localhost:<port>
```

`application-prod.yml`

```yaml
gateway:
  uris:
    newcastle-service: https://newcastle-service.onrender.com
    your-city-service: <your-city-uri>
```

This configuration ensures that the main gateway properly proxies requests to your city's backend microservice while
applying the necessary authentication filters.

---

# Happy Coding 🧑‍💻
