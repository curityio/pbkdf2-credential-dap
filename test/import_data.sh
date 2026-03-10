#!/bin/bash

# Ensure that the script runs in the script's parent directory for the relative paths to work
cd "$(dirname "${BASH_SOURCE[0]}")"

CONTAINER_ID=$(docker ps | grep idsvr | awk '{print $1}')
docker exec -it "$CONTAINER_ID" bash -c "JDBC_URL=jdbc:hsqldb:file:/opt/idsvr/var/db/db JDBC_USERNAME=sa JDBC_PASSWORD= JAVA_OPTS= idsvr -L curity/changelog.xml" > upgrade.sql
