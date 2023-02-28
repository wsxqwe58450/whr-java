# 该镜像所依赖的基础镜像
FROM openjdk:8

COPY *.jar /app.jar

CMD ["--server.port=8090"]

#暴露的端口
EXPOSE 8090

#维护者的联系方式
MAINTAINER wangshixiu20@163.com

#执行的命令
ENTRYPOINT ["java","-jar","app.jar"]

