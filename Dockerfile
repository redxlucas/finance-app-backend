# Etapa 1: Imagem de Testes
FROM gradle:8.4.0-jdk21 AS test

WORKDIR /home/finance-app

COPY build.gradle .
COPY settings.gradle .
COPY src /home/finance-app/src

# Definir a variável de ambiente ENVIRONMENT para o perfil de teste
ENV ENVIRONMENT=${ENVIRONMENT}

# Executar os testes com o perfil "test"
RUN gradle test -Dspring.profiles.active=test

# Etapa 2: Imagem de Build
FROM gradle:8.4.0-jdk21 AS build

WORKDIR /home/finance-app

COPY build.gradle .
COPY settings.gradle .
COPY src /home/finance-app/src

# Definir a variável de ambiente ENVIRONMENT
ENV ENVIRONMENT=${ENVIRONMENT}

# Construir o projeto
RUN gradle clean build 

# Etapa 3: Imagem Final
FROM openjdk:21

WORKDIR /home/finance-app

# Copiar o JAR construído da etapa de build
COPY --from=build /home/finance-app/build/libs/financeapp-0.0.1-SNAPSHOT.jar .

# Definir o ponto de entrada do contêiner
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$ENVIRONMENT financeapp-0.0.1-SNAPSHOT.jar"]