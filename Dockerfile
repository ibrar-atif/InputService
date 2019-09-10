FROM openjdk:8
ADD target/Input-Service.jar Input-Service.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","Input-Service.jar"]