#!/bin/bash

#
# Check prerequisites
#
if [ "$LICENSE_FILE_PATH" == '' ]; then
  echo '>>> Please provide a LICENSE_FILE_PATH environment variable with the path to a Curity Identity Server license file'
  exit 1
fi

export LICENSE_KEY=$(cat "$LICENSE_FILE_PATH" | jq -r .License)
if [ "$LICENSE_KEY" == '' ]; then
  echo '>>> An invalid license file was provided for the Curity Identity Server'
  exit 1
fi

docker run -it \
-v ../build/libs/pbkdf2-credential-dap-1.0.0.jar:/opt/idsvr/usr/share/plugins/pbkdf2-dap/pbkdf2-dap.jar  \
-v ./changelog.xml:/opt/idsvr/etc/liquibase/curity/changelog.xml \
-v ./curity-config.xml:/opt/idsvr/etc/init/config.xml \
-p 6749:6749 -p 8443:8443 --rm -e LOGGING_LEVEL=DEBUG -e ADMIN=true -e LICENSE_KEY=$LICENSE_KEY --name idsvr \
curity.azurecr.io/curity/idsvr

