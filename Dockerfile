FROM openjdk:8-jdk-alpine
EXPOSE 8080

ARG VAR_PROFILE
ENV env_var_profile=${VAR_PROFILE}

ADD ./target/load-ips.jar app.jar
CMD ["/bin/sh"]
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${env_var_profile}", "/app.jar"]