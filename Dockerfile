FROM eclipse-temurin:21-jre
# Safer to run as non-root
RUN useradd -ms /bin/bash appuser
WORKDIR /app
COPY app.jar /app/app.jar

# Optional JVM tuning
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8080
USER appuser
CMD ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
