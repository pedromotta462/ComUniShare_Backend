# Use a imagem oficial do OpenJDK 17 como base
FROM openjdk:17-jdk-alpine

# Criar usuário
RUN addgroup -S app && adduser -S app -G app
USER app

# Defina o diretório de trabalho
WORKDIR /app

# Copie os arquivos necessários
COPY . .

# Construa a aplicação Spring Boot
RUN sh -c "./mvnw clean package"

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
CMD ["java", "-jar", "target/ComUniShare-0.0.1-SNAPSHOT.war"]
