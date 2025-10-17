#!/bin/bash
# http://localhost:8080/employees/list

# --- Switch app to SAML mode ---
export EDB_AUTHENTICATION=saml

# --- App base URL (used for ACS below) ---
export SERVER_BASE_URL='http://localhost:8080'

# --- Your SP entity ID (keep as you had it) ---
#export EDB_SAML_ENTITY_ID='urn:employeedb'
export EDB_SAML_ENTITY_ID='employeedb.fortidemo.ch'

# --- IMPORTANT: use the tenant-specific IdP metadata URL (NOT /saml-idp/metadata) ---
export EDB_SAML_IDP_METADATA_URI='https://fortiauth.fortidemo.ch/saml-idp/employeedb/metadata/'
export EDB_SAML_IDP_LOGOUT_URL='https://fortiauth.fortidemo.ch/saml-idp/employeedb/logout/'
export EDB_SAML_POST_LOGOUT_REDIRECT='/'

# --- Spring relaxed-binding equivalents to ensure Spring Security SAML picks it up ---
export SPRING_SECURITY_SAML2_RELYINGPARTY_REGISTRATION_FORTIAUTH_ASSERTINGPARTY_METADATA_URI="$EDB_SAML_IDP_METADATA_URI"
export SPRING_SECURITY_SAML2_RELYINGPARTY_REGISTRATION_FORTIAUTH_ENTITY_ID="$EDB_SAML_ENTITY_ID"
export SPRING_SECURITY_SAML2_RELYINGPARTY_REGISTRATION_FORTIAUTH_ASSERTION_CONSUMER_SERVICE_LOCATION="$SERVER_BASE_URL/login/saml2/sso/fortiauth"

export LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY_SAML2=DEBUG
export LOGGING_LEVEL_ORG_SPRINGSECURITY=DEBUG
export LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=DEBUG

# --- Run with the same profile as your -D example ---
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
