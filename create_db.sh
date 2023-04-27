#!/bin/bash

set -e
set -u

echo "Creating operator database 'ku_users'"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
CREATE DATABASE "ku_users";
CREATE USER user_for_ku_users WITH PASSWORD 'password' SUPERUSER;
ALTER DATABASE ku_users OWNER TO user_for_ku_users;
\connect ku_users
SET ROLE user_for_ku_users;
CREATE SCHEMA users_schema;
ALTER ROLE user_for_ku_users SET search_path TO users_schema,public;

EOSQL
