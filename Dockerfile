FROM maven:3.6.3-openjdk-14-slim AS build

COPY settings.xml /usr/share/maven/conf/

COPY pom.xml pom.xml
COPY sis-api/pom.xml sis-api/pom.xml
COPY sis-model/pom.xml sis-model/pom.xml
COPY sis-base/pom.xml sis-base/pom.xml

RUN mvn dependency:go-offline package -B

COPY sis-api/src sis-api/src
COPY sis-model/src sis-model/src
COPY sis-base/src sis-base/src

RUN mvn install -Prunnable

FROM openjdk:14-ea-jdk-alpine
USER root

RUN mkdir service

COPY --from=build /sis-base/target/ /service/
COPY /wz /service/wz
COPY config.yaml /service/

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.5.0/wait /wait

RUN chmod +x /wait

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005

EXPOSE 5005

CMD /wait && java --enable-preview -jar /service/sis-base-1.0-SNAPSHOT.jar -Xdebug