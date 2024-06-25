FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/CuentaMovimientosService-0.0.1-SNAPSHOT.jar CuentaMovimientosService.jar
ENTRYPOINT ["java","-jar","/CuentaMovimientosService.jar"]