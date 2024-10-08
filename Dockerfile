FROM openjdk:17-oracle

VOLUME /tmp

ARG JAR_FILE=/build/libs/*.jar

COPY ${JAR_FILE} SNAPSHOT.jar

ENTRYPOINT ["nohup", "java", "-jar", "app.jar", "&"]