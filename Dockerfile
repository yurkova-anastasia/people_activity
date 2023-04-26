#FROM liquibase/liquibase:4.6.2
#
#USER root
#
#ENV DB_HOST=localhost \
#    DB_PORT=5432\
#    DB_PASS=postgres \
#    DB_USER=root \
#    DB_NAME=users \
#    DB_SCHEMA=users_schema
#
#RUN apt-get update && apt-get install -y postgresql-client
#
#COPY create_db.sh /create_db.sh
#RUN chmod +x /create_db.sh
#
#ENTRYPOINT ["/create_db.sh"]
#
#CMD ["tail", "-f", "/dev/null"]


FROM postgres:latest

# Копирование sh скрипта внутрь контейнера
COPY create_db.sh /docker-entrypoint-initdb.d/

# Установка прав на sh скрипт
RUN chmod +x /docker-entrypoint-initdb.d/create_db.sh

ENTRYPOINT ["/create_db.sh"]