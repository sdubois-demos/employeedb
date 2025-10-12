#!/bin/bash

EDB_AUTHENTICATION=ldap \
EDB_LDAP_URL=ldap://fortiauth.fortidemo.ch:389 \
EDB_LDAP_BASE=dc=fortidemo,dc=ch \
EDB_LDAP_MANAGER_DN='uid=brian,ou=Users,dc=fortidemo,dc=ch' \
EDB_LDAP_MANAGER_PWD='im2fast4u$' \
EDB_LDAP_USER_DN_PATTERN='uid={0},ou=Users' \
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
