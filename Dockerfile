FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /springboot-2.2-junit-5/lib
COPY ${DEPENDENCY}/META-INF/springboot-2.2-junit-5/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /springboot-2.2-junit-5
ENTRYPOINT ["java","-cp","springboot-2.2-junit-5:springboot-2.2-junit-5/lib/*","com.viooh.sandbox.TodoApplication"]