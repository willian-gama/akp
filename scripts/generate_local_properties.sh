#!/bin/sh

LOCAL_PROPERTIES="local.properties"
export LOCAL_PROPERTIES

if [ ! -f "$LOCAL_PROPERTIES" ]; then
    touch $LOCAL_PROPERTIES

    echo "api_key=$API_KEY" >> $LOCAL_PROPERTIES
    echo "gpr_username=$GPR_USERNAME" >> $LOCAL_PROPERTIES
    echo "gpr_key=$GPR_KEY" >> $LOCAL_PROPERTIES
    echo "sonar_token=$SONAR_TOKEN" >> $LOCAL_PROPERTIES

    cat $LOCAL_PROPERTIES
fi
