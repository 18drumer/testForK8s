FROM eclipse-temurin:17-jre

WORKDIR /app

# root 가 아닌 사용자로 실행
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring:spring

COPY build/libs/app.jar app.jar

# 컨테이너 기동 시 dev 프로필로 실행 (필요 시 -e SPRING_PROFILES_ACTIVE=... 로 덮어쓰기 가능)
ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
