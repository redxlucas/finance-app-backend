# # FROM gradle:8.4.0-jdk21 AS test

# # WORKDIR /home/finance-app

# # COPY build.gradle .
# # COPY settings.gradle .
# # COPY src /home/finance-app/src

# # ENV ENVIRONMENT=${ENVIRONMENT}

# # RUN gradle test -Dspring.profiles.active=test

# FROM gradle:8.4.0-jdk21 AS build

# WORKDIR /home/finance-app

# COPY build.gradle .
# COPY settings.gradle .
# COPY src /home/finance-app/src

# ENV ENVIRONMENT=${ENVIRONMENT}

# RUN gradle clean build -x test

# FROM openjdk:21

# WORKDIR /home/finance-app

# COPY --from=build /home/finance-app/build/libs/financeapp-0.0.1-SNAPSHOT.jar .

# ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$ENVIRONMENT financeapp-0.0.1-SNAPSHOT.jar"]

# Novo Dockerfile

# FROM gradle:8.4.0-jdk21 AS build

# RUN apt-get update
# RUN apt-get install openjdk-17-jdk -y

# COPY . . 

# RUN gradle clean build -x test

# FROM openjdk:21

# EXPOSE 8080

# COPY --from=build 

# Etapa de build com Gradle 8.12.1 e Java 21
# FROM gradle:8.12.1-jdk21 AS build

# WORKDIR /app
# COPY . .

# ENV GRADLE_USER_HOME=/app/.gradle

# RUN gradle clean build --no-daemon

# # Etapa de execução com Java 21
# FROM openjdk:21-jdk-slim

# WORKDIR /app
# EXPOSE 8080

# COPY --from=build /app/build/libs/*.jar app.jar

# ENTRYPOINT ["java", "-jar", "app.jar"]

FROM gradle:8.12.1-jdk21 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

#
# Package stage
#
FROM openjdk:21

COPY --from=build /home/gradle/src/build/libs/financeapp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
