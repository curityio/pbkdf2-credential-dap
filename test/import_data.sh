#!/bin/bash

CONTAINER_ID=$(docker ps | grep idsvr | awk '{print $1}')
docker exec -it "$CONTAINER_ID" bash -c "JDBC_URL=jdbc:hsqldb:file:/opt/idsvr/var/db/db JDBC_USERNAME=sa JDBC_PASSWORD= idsvr -L curity/changelog.xml" > upgrade.sql
