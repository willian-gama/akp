#!/bin/sh

LOCAL_PROPERTIES="local.properties"
touch $LOCAL_PROPERTIES

# Secret keys: https://drive.google.com/drive/u/0/folders/1UXqMF2D3OijaLKPhTj6NDnX8zut3kQhW
# Must be included in Repository secrets: https://github.com/willian-gama/akc/settings/secrets/actions
{
  # The Movie API
  echo "api_key=$API_KEY"
  # Github packages
  echo "gpr_username=$GPR_USERNAME"
  echo "gpr_key=$GPR_KEY"
  # Sonar
  echo "sonar_token=$SONAR_TOKEN"
  echo "sonar_project_key=$SONAR_PROJECT_KEY"
  echo "sonar_organization_key=$SONAR_ORGANIZATION_KEY"
  echo "sonar_project_name=$SONAR_PROJECT_NAME"
}  >> $LOCAL_PROPERTIES

cat $LOCAL_PROPERTIES