# Build application
FROM openjdk:11-jdk as build

# Create an application directory
RUN mkdir -p /app

# 작업을 수행할 디렉토리를 설정 /app
WORKDIR /app

# 생성된 이미지 안으로 mvn 실행 파일을 복사
COPY mvnw .
COPY .mvn .mvn

# pom파일 복사
COPY pom.xml .

# 메이븐 오프라인 모드로 모든 의존성 다운로 받음
# 미리 다운받은 로컬 저장소를 찾게되고 인터넷이 안되는 환경에서도 메이븐을 사용 가능
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

# 소스 복사
COPY src src

# application 패키징
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:11-jre

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENV PROFILE=prod

ENTRYPOINT ["java","-cp","app:app/lib/*","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=${PROFILE}","nice.jongwoo.SpringReactCalendarTodoApplication"]
