# Use a base do OpenJDK
FROM openjdk:21-slim

# Adiciona o volume padrão
VOLUME /tmp

# Define o argumento para o arquivo JAR
ARG JAR_FILE=target/boleto-0.0.1-SNAPSHOT.jar

# Copia o JAR para a imagem
COPY ${JAR_FILE} boleto.jar

# Define o entrypoint para executar a aplicação
ENTRYPOINT ["java","-jar","/boleto.jar"]