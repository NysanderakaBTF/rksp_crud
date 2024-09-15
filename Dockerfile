# Stage 1: Build the application
FROM openjdk:17-jdk-slim-buster AS build
WORKDIR /app
COPY . /app
RUN ./gradlew build

# Stage 2: Run the application
FROM openjdk:17-jdk-slim-buster

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENV DB_HOST=0.0.0.0
ENV DB_PORT=5430
ENV DB_DATABASE=postgres
ENV DB_USERNAME=fox
ENV DB_PASSWORD=fox
ENV APP_PORT=8000
EXPOSE 8000

# Install wget
RUN apt update && apt install -y wget

# Download image
RUN wget https://www.mirea.ru/upload/medialibrary/80f/MIREA_Gerb_Colour.png

# Add endpoints and expose necessary ports
CMD java -jar app.jar

# Metadata
LABEL author="Резников Григорий"
LABEL group="IKBO-10-21"

# ONBUILD command
ONBUILD RUN echo "Сборка и запуск произведены. Автор: Резников Григорий"

# Build the Docker image: docker build -t my-spring-app .
# Run the Docker container: docker run -p 8080:8080 --env DB_PORT=5432 my-spring-app
