FROM hengboy/openjdk:openj9-jre17.0.6

# set environment
ENV BASE_DIR="/home/on-security-console" \
    CLASSPATH=".:/home/on-security-console/conf:$CLASSPATH" \
    JAVA_HOME="/opt/java/openjdk" \
    JAVA="/opt/java/openjdk/bin/java" \
    JVM_XMS="1g" \
    JVM_XMX="1g" \
    JVM_XMN="512m" \
    JVM_MS="128m" \
    JVM_MMS="320m" \
    TIME_ZONE="Asia/Shanghai" \
    CONSOLE_VERSION=0.1.1

WORKDIR $BASE_DIR

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends wget
RUN wget --no-check-certificate https://github.com/On-Security/on-security/releases/download/v${CONSOLE_VERSION}/on-security-console-${CONSOLE_VERSION}.tar.gz -P /home
RUN tar -xzvf /home/on-security-console-${CONSOLE_VERSION}.tar.gz -C /home \
    && rm -rf /home/on-security-console-${CONSOLE_VERSION}.tar.gz /home/on-security-console/conf/application.yml /home/on-security-console/conf/on-security-schema.sql


ADD bin/startup.sh bin/docker-startup.sh
ADD conf/application.yml conf/application.yml

RUN chmod +x bin/docker-startup.sh

EXPOSE 59527
ENTRYPOINT ["bin/docker-startup.sh"]