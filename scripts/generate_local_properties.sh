#!/bin/sh

LOCAL_PROPERTIES="local.properties"
export LOCAL_PROPERTIES

if [ ! -f "$LOCAL_PROPERTIES" ]; then
    touch $LOCAL_PROPERTIES
    {
      echo "api_key=${{ secrets.API_KEY }}"
      echo "github_user_id=${{ secrets.GITHUB_USER_ID }}"
      echo "github_key=${{ secrets.GITHUB_KEY }}"
    } >> $LOCAL_PROPERTIES

    cat $LOCAL_PROPERTIES
fi
