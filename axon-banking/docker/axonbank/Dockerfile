FROM openjdk:8u131-jre-alpine
MAINTAINER Maksim Kostromin <daggerok@gmail.com>
RUN mkdir -p /opt/app \
 && touch /opt/app/app.jar \
 && apk --no-cache add curl bash
WORKDIR /opt/app
VOLUME ["/opt/app"]
ENTRYPOINT ["java", "-jar", "./app.jar"]
EXPOSE 8002
HEALTHCHECK --interval=5s --timeout=5s --retries=5 CMD curl -f http://127.0.0.1:8002/health || exit 1
COPY ./axonbank/build/libs/*.jar ./app.jar
