#!/bin/bash
set -e
set -u

echo "Creating operator database 'ku_users'"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
CREATE DATABASE "ku_users";
CREATE USER ku_users_app WITH PASSWORD 'password' SUPERUSER;
ALTER DATABASE ku_users OWNER TO ku_users_app;
\connect ku_users
SET ROLE ku_users_app;
CREATE SCHEMA users_schema;
ALTER ROLE ku_users_app SET search_path TO users_schema,public;

EOSQL
