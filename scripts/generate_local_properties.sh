#!/bin/sh

LOCAL_PROPERTIES="local.properties"
export LOCAL_PROPERTIES

if [ ! -f "$LOCAL_PROPERTIES" ]; then
    touch $LOCAL_PROPERTIES
    echo "${{ secrets.API_KEY }}"

    {
      echo "api_key=$API_KEY"
      echo "gpr_username=$GPR_USERNAME"
      echo "gpr_key=$GPR_KEY"
    } >> $LOCAL_PROPERTIES

    cat $LOCAL_PROPERTIES
fi
