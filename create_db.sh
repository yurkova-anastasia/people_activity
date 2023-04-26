#!/bin/bash

    DB_HOST=postgres
    DB_PORT=5432
    DB_PASS=postgres
    DB_USER=root
    DB_NAME=users
    DB_SCHEMA=users_schema

    psql -U postgres -c "CREATE DATABASE ${DB_NAME};"
    psql -U postgres -c "CREATE USER ${DB_USER} WITH PASSWORD '${DB_PASS}';"
    psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE ${DB_NAME} TO ${DB_USER};"
    psql -d ${DB_NAME} -U ${DB_USER} -c "CREATE SCHEMA ${DB_SCHEMA};"
    psql -d ${DB_NAME} -U ${DB_USER} -c "ALTER ROLE ${DB_USER} SET search_path = ${DB_SCHEMA};"

