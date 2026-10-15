FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia directamente el .jar que GitHub Actions descargó en la carpeta target/
COPY target/*.jar app.jar

ENV APP_ENV=production
EXPOSE 3001

ENTRYPOINT [ "java", "-jar", "app.jar" ]