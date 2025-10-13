export EDB_AUTHENTICATION=ldap
export EDB_LDAP_URL=ldap://fortiauth.fortidemo.ch:389
export EDB_LDAP_BASE=dc=fortidemo,dc=ch
export EDB_LDAP_MANAGER_DN='cn=ldap-reader,ou=svc,dc=fortidemo,dc=ch'
export EDB_LDAP_MANAGER_PWD='••••••'
export EDB_LDAP_USER_DN_PATTERN='uid={0},ou=people'
./mvnw spring-boot:run

export EDB_AUTHENTICATION=saml
export SERVER_BASE_URL=http://localhost:8080
export EDB_SAML_ENTITY_ID=urn:employeedb
export EDB_SAML_IDP_METADATA_URI=https://fortiauth.fortidemo.ch/saml-idp/metadata
./mvnw spring-boot:run
