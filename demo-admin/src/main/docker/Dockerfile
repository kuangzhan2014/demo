FROM frolvlad/alpine-java:jdk8-slim
VOLUME /tmp

WORKDIR /app

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
       && echo "Asia/Shanghai" > /etc/timezone
#RUN apk add bash curl
# copy code
ADD ./target/brand-admin.jar ./brand-admin.jar
RUN sh -c 'touch /app/demo-admin.jar'

ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
EXPOSE 8080
CMD exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar demo-admin.jar
