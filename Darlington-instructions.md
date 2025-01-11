# Alvin Kho - Darlington microservices

## Pre-requisites
- Docker Desktop must be installed

## Steps

1. cd into main directory of the microservice: cd "backend/darlington/individual-assessment/main"
2. run docker-compose build
3. run docker-compose up -d

## If Error happen due to data folder having issue:
1. Delete data folder
2. run docker-compose build
3. run docker-compose up -d

### By now, the data file is empty. To populate:
1. Using postman, enter this url : "http://localhost:8087/api/electricalProvider/generateTestData", the data file should be populated
2. Then, run this url:
   - http://localhost:8087/api/smartCity/electricalProvider/507f1f77bcf86cd799439010/allSummary
   - http://localhost:8087/api/smartCity/electricalProvider/507f1f77bcf86cd799439011/allSummary
   - http://localhost:8087/api/smartCity/electricalProvider/507f1f77bcf86cd799439012/allSummary
3. If all is working as intended, you should see the results appearing at the front-end.
