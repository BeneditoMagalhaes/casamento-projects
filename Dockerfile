# Etapa 1: compila o projeto com Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# baixa as dependencias antes de copiar o codigo, para aproveitar cache do Docker
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# Etapa 2: imagem final, so com o Java Runtime + o .jar gerado (menor e mais rapida)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/wedding-backend.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
