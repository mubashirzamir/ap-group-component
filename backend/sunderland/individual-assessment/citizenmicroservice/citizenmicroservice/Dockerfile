# Use Maven to build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/*.jar application.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
CMD ["java", "-Xmx2048M", "-jar", "application.jar"]