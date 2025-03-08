FROM gradle:8.4.0-jdk21 AS test

WORKDIR /home/finance-app

COPY build.gradle .
COPY settings.gradle .
COPY src /home/finance-app/src

ENV ENVIRONMENT=${ENVIRONMENT}

RUN gradle test -Dspring.profiles.active=test

FROM gradle:8.4.0-jdk21 AS build

WORKDIR /home/finance-app

COPY build.gradle .
COPY settings.gradle .
COPY src /home/finance-app/src

ENV ENVIRONMENT=${ENVIRONMENT}

RUN gradle clean build 

FROM openjdk:21

WORKDIR /home/finance-app

COPY --from=build /home/finance-app/build/libs/financeapp-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$ENVIRONMENT financeapp-0.0.1-SNAPSHOT.jar"]