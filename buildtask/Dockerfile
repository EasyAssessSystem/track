FROM daocloud.io/java:8
RUN mkdir /app/
RUN mkdir /app/logs
RUN mkdir /app/target
RUN mkdir /app/buildtask
COPY ./target/track-service-0.0.1.jar /app/target
COPY ./buildtask/entrypoint.sh /app/
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
WORKDIR /app/
EXPOSE 9290 9290
CMD ["./entrypoint.sh"]