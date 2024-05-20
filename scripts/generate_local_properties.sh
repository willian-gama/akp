#!/bin/sh

LOCAL_PROPERTIES="local.properties"
touch $LOCAL_PROPERTIES

{
  echo "api_key=$API_KEY"
  echo "gpr_username=$GPR_USERNAME"
  echo "gpr_key=$GPR_KEY"
  echo "sonar_token=$SONAR_TOKEN"
}  >> $LOCAL_PROPERTIES

cat $LOCAL_PROPERTIES