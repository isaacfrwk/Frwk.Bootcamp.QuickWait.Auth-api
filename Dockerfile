FROM openjdk:11-slim

LABEL maintainer="Luis Eduardo de Matos <luisedu.unifal@gmail.com>"

ENTRYPOINT ["java", "-jar", "/app/auth.jar"]

ARG JAR_FILE

ADD ${JAR_FILE} /app/auth.jar