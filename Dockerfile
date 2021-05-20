FROM jboss/keycloak
ENTRYPOINT [ "/opt/jboss/tools/docker-entrypoint.sh", "-Dkeycloak.profile.feature.upload_scripts=enabled" ]