# KF7014 Advanced Programming - Group Instructions

## Group Members

| Name                       | Email                       | City          | Ports |
|----------------------------|-----------------------------|---------------|-------|
| Alvin Kho Yien Yang        | w24017214@northumbria.ac.uk | Darlington    |       |  
| Avinash Saha               | w24036703@northumbria.ac.uk | Durham        |       | 
| Mubashir Zamir             | w24029487@northumbria.ac.uk | Newcastle     | 8081  | 
| Muhammad Murtaz Amir Naqvi | w24034050@northumbria.ac.uk | Middlesbrough |       |   
| Shubham Goswami            | w24038579@northumbria.ac.uk | Sunderland    |       |

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
   mvn clean install
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

### 2. Frontend Development

To start developing the frontend, focus on modifying the files for your specific city located at:

- `/frontend/src/pages/Dashboard/Cities/YourCityName`

For example, you can refer to `/frontend/src/pages/Dashboard/Cities/Newcastle` for guidance on how to structure your
city’s dashboard.

### 3. Configuration

#### API Base Path

Add your city’s API base path to the `.env` file in the `/frontend` directory. For example:

- `VITE_NEWCASTLE_API_BASE_URL=http://localhost:8081`

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

## Running Mubashir's Backend Microservices (Newcastle)

To run the backend microservices for Newcastle, follow the instructions below:

1. Navigate to the Newcastle directory:

   ```bash
   cd /backend/newcastle/individual-assessment/individual-assessment/main
   ```
2. Build the docker containers:

   ```bash
   docker-compose build
   ```

3. Run the docker containers:

   ```bash
    docker-compose up -d
    ```

If these microservices are running, you'll be able to see the data in the Newcastle dashboard.
   
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
   - **Do not push your changes directly to the `main` branch**. Always create a Pull Request and wait for it to be reviewed and approved.

By following these steps, we can maintain a clean workflow and minimize merge conflicts.

---

# Happy Coding 🧑‍💻
