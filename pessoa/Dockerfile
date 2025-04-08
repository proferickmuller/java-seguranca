# A imagem OpenJDK podem ter problemas com pacotes ou repositórios
# FROM openjdk:21-slim

# Use a base do eclipse-temurin, uma versão mais recente, que é baseada no OpenJDK e geralmente tem repositórios mais estáveis
FROM eclipse-temurin:latest

# Adiciona o volume padrão
VOLUME /tmp

# Às vezes, a imagem base pode ter repositórios desatualizados. Aqui, substituiremos os repositórios no Dockerfile explicitamente
RUN sed -i 's|http://deb.debian.org/debian|http://archive.debian.org/debian|g' /etc/apt/sources.list

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

# Configuração para fontes personalizadas (opcional)
# Copia fontes adicionais se necessário
# COPY ./fonts /usr/share/fonts/
# RUN fc-cache -fv

# Define o argumento para o arquivo JAR
ARG JAR_FILE=target/pessoa-0.0.1-SNAPSHOT.jar

# Copia o JAR para a imagem
COPY ${JAR_FILE} pessoa.jar

# Define o entrypoint para executar a aplicação com o modo "headless" habilitado ao executar a aplicação
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar","/pessoa.jar"]