FROM openjdk:17-jdk-slim
MAINTAINER rt.sw.studio@gmail.com
COPY dist/liveboards-0.1.jar liveboards-0.1.jar
COPY dist/liveboards-config.json liveboards-config.json
COPY dist/lib/* lib/
COPY examples/boards/* examples/boards/
COPY examples/tables/* examples/tables/
EXPOSE 8888
ENTRYPOINT ["java", "-cp", ".:liveboards-0.1.jar:lib/*", "-Dconfig.file=liveboards-config.json", "com.rtsw.liveboards.server.Server"]