FROM registry.redhat.io/ubi8/ubi-minimal:8.4-208
USER root
RUN microdnf install java-1.8.0-openjdk-devel -y \
    && microdnf install maven -y \
    && microdnf install tar -y 
EXPOSE 8080/tcp
ENV DATABASE_HOSTNAME="springdbhost" DATABASE_PORT="5432" DATABASE_NAME="springdb" DATABASE_USER="springuser" DATABASE_PASSWORD="passw0rd"
COPY src /home/app/src
COPY pom.xml /home/app
RUN mkdir /properties && touch /properties/additional.properties
RUN mvn -f /home/app/pom.xml clean install && cp /home/app/target/*.jar /home/app/application.jar && rm /home/app/target/*.jar
ENTRYPOINT ["java","-jar","/home/app/application.jar"]
