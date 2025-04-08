# Use a base do OpenJDK
FROM docker.io/library/maven:3.9.9-eclipse-temurin-21-alpine AS build

# Adiciona o volume padrão
VOLUME /tmp

COPY ./pom.xml ./pom.xml
RUN --mount=type=cache,target=/root/.m2,rw mvn dependency:go-offline -B
COPY . .

RUN mvn clean
RUN mvn package -Dmaven.test.skip

FROM docker.io/library/eclipse-temurin:21-alpine

# Define o argumento para o arquivo JAR
ARG JAR_FILE=target/boleto-0.0.1-SNAPSHOT.jar

# Copia o JAR para a imagem
COPY --from=build ${JAR_FILE} /boleto.jar

# Define o entrypoint para executar a aplicação
ENTRYPOINT ["java","-jar","/boleto.jar"]