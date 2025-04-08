# syntax=docker/dockerfile:experimental

# A imagem OpenJDK podem ter problemas com pacotes ou repositórios
# FROM openjdk:21-slim

# Use a base do eclipse-temurin, uma versão mais recente, que é baseada no OpenJDK e geralmente tem repositórios mais estáveis
FROM docker.io/library/maven:3.9.9-eclipse-temurin-21-jammy AS build

# Adiciona o volume padrão
VOLUME /tmp

# Às vezes, a imagem base pode ter repositórios desatualizados. Aqui, substituiremos os repositórios no Dockerfile explicitamente
# RUN sed -i 's|http://deb.debian.org/debian|http://archive.debian.org/debian|g' /etc/apt/sources.list

# Instala as dependências gráficas e fontes necessárias para renderização de fontes e PDF no JasperReports
RUN apt-get clean && apt-get update && apt-get install -y --no-install-recommends \
    fontconfig \
    libfreetype6 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    fonts-dejavu-core \
    fonts-dejavu-extra \
    && rm -rf /var/lib/apt/lists/*

COPY ./pom.xml ./pom.xml

# RUN --mount=type=cache,target=/root/.m2,rw mvn -B package

RUN --mount=type=cache,target=/root/.m2,rw mvn dependency:go-offline -B

COPY . .

RUN mvn clean
RUN mvn package -Dmaven.test.skip
#
### Define o entrypoint para executar a aplicação com o modo "headless" habilitado ao executar a aplicação
FROM docker.io/library/eclipse-temurin:21

## Instala as dependências gráficas e fontes necessárias para renderização de fontes e PDF no JasperReports
RUN apt-get clean && apt-get update && apt-get install -y --no-install-recommends \
    fontconfig \
    libfreetype6 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    fonts-dejavu-core \
    fonts-dejavu-extra \
    iputils-ping \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /target/pessoa-0.0.1-SNAPSHOT.jar /pessoa.jar

COPY certs /app/certs

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar","/pessoa.jar"]