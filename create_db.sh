#!/bin/bash

set -e
set -u

echo "Creating operator database 'ku_users'"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
CREATE DATABASE "ku_users";
CREATE USER root WITH PASSWORD 'password' SUPERUSER;
ALTER DATABASE ku_users OWNER TO root;
\connect ku_users
SET ROLE root;
CREATE SCHEMA users_schema;
ALTER ROLE root SET search_path TO users_schema,public;

EOSQL