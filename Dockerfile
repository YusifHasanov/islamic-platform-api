# Use the base image
FROM openjdk:19-alpine

# Set the environment variable
ENV DEVELOPER_KEY=AIzaSyCOlR3C1Mrf35n-_GozHaPuc9KJGV_Hd90

# Set the working directory
WORKDIR /app

# Copy the application files
COPY ./target /app

EXPOSE 8080:8080

# Specify the command to run when the container starts
CMD ["java", "-jar", "/app/esm-0.0.1-SNAPSHOT.jar"]